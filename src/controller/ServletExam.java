package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import model.Exam;
import service.ServiceExam;

//@WebFilter(urlPatterns = {"/views/pages/exams", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/exams", "/views/errors/default_error" })
//@WebServlet("/views/pages/exams")
@WebServlet(name = "ServletExam", urlPatterns = "/views/pages/ServletExam")

public class ServletExam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceExam serviceExam;

	public void init() {
		serviceExam = new ServiceExam();
	}

	public ServletExam() {
		super();
	}

	public boolean isEmptyCbox(String input) {
		if (input != null && input.equals("Select")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOutOfRange(String input) {
		if (input.length() > 60 || input.length() < 3) {
			return true;
		} else {
			return false;
		}
	}

	public LocalDate getDateWithoutTimeFromString(String dateStr) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate ld = dtfOut.parseLocalDate(dateStr);
		return ld;
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceExam", serviceExam);
		request.setAttribute("exams", serviceExam.getAllExams().toArray());
		request.setAttribute("feeTypes", serviceExam.selectFeeTypes().toArray());
		request.setAttribute("depNames", serviceExam.selectDepNames().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("exams.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String createParamId = request.getParameter("btnCreate");
			String editParamId = request.getParameter("btnEdit");
			String displayParamId = request.getParameter("btnDisplay");
			String view01ParamId = request.getParameter("btnView01");
			String view02ParamId = request.getParameter("btnView02");

			if (view01ParamId != null || view02ParamId != null) {
				createParamId = editParamId = displayParamId = null;
				processRequest(request, response);

			} else if (createParamId != null) {
				request.setAttribute("createParam", "selected");
				processRequest(request, response);

			} else if (editParamId != null) {
				int id = Integer.parseInt(editParamId);
				if (id > 0) {
					request.setAttribute("editParam", "selected");
					request.setAttribute("exam", serviceExam.getExamById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("exam", serviceExam.getExamById(id));
				processRequest(request, response);

			} else {
				processRequest(request, response);
			}
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// Required Variables
			int id, result;
			String examType, examSub, examDate, examTime, remarks, feeType, depName, error, emptyFields, outOfRange,
					dateOutOfRange, duplicateVal, idNotfound, sqlIssue;

			id = result = 0;
			examType = examSub = examDate = examTime = remarks = feeType = depName = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";
			dateOutOfRange = "Date out of range! Insert date between 2000 and 2021.";

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {

				try {
					// User Input Variables
					examType = request.getParameter("crExmType");
					examSub = request.getParameter("crExmSub");
					examDate = request.getParameter("crExmDate");
					examTime = request.getParameter("crExmTime");
					remarks = request.getParameter("crExmRmk");
					feeType = request.getParameter("crExmFee");
					depName = request.getParameter("crExmDept");

					if (examType.isEmpty() || examSub.isEmpty() || examDate.isEmpty() || examTime.isEmpty()
							|| isEmptyCbox(feeType) || isEmptyCbox(depName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						if (isOutOfRange(examType) || isOutOfRange(examSub) || examTime.length() < 4 || examTime.length() > 30
								|| remarks.length() > 255) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							LocalDate myDate = getDateWithoutTimeFromString(examDate);
							int myYear = myDate.getYear();
							int feeId = serviceExam.getFeeIdByFeeType(feeType);
							int depId = serviceExam.getDepIdByDepName(depName);

							if (myYear < 2000 || myYear > 2021 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceExam.checkDuplicateExam(examType, examSub, myDate)) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								} else {
									Exam exm = new Exam();
									exm.setExmType(examType);
									exm.setExmSub(examSub);
									exm.setExmDate(myDate);
									exm.setExmTime(examTime);
									exm.setExmRmk(remarks);
									exm.setExmFee(feeId);
									exm.setExmDept(depId);
									result = serviceExam.addExam(exm);
									if (result == 1) {
										processRequest(request, response);
									} else {
										request.setAttribute(error, sqlIssue);
										request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					request.setAttribute(error, ex.getMessage());
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				}
			}
			/*************************************************
			 *************************************** Edit Form
			 *************************************************/
			else if (request.getParameter("postUpdate") != null) {

				try {
					// User Input Variables
					examType = request.getParameter("upExmType");
					examSub = request.getParameter("upExmSub");
					examDate = request.getParameter("upExmDate");
					examTime = request.getParameter("upExmTime");
					remarks = request.getParameter("upExmRmk");
					feeType = request.getParameter("upExmFee");
					depName = request.getParameter("upExmDept");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (examType.isEmpty() || examSub.isEmpty() || examDate.isEmpty() || examTime.isEmpty()
							|| isEmptyCbox(feeType) || isEmptyCbox(depName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						if (isOutOfRange(examType) || isOutOfRange(examSub) || examTime.length() < 4 || examTime.length() > 30
								|| remarks.length() > 255) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							LocalDate myDate = getDateWithoutTimeFromString(examDate);
							int myYear = myDate.getYear();
							int feeId = serviceExam.getFeeIdByFeeType(feeType);
							int depId = serviceExam.getDepIdByDepName(depName);

							if (myYear < 2000 || myYear > 2021 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceExam.checkDuplicateExamOnEdit(id, examType, examSub, myDate) == false) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								} else {
									Exam exm = new Exam();
									exm.setExmId(id);
									exm.setExmType(examType);
									exm.setExmSub(examSub);
									exm.setExmDate(myDate);
									exm.setExmTime(examTime);
									exm.setExmRmk(remarks);
									exm.setExmFee(feeId);
									exm.setExmDept(depId);
									result = serviceExam.updateExam(exm);
									if (result == 1) {
										processRequest(request, response);
									} else {
										request.setAttribute(error, sqlIssue);
										request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					request.setAttribute(error, ex.getMessage());
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				}

			}
			/*************************************************
			 ************************************* Delete Form
			 *************************************************/
			else if (request.getParameter("postDelete") != null) {
				id = Integer.parseInt(request.getParameter("postDelete"));
				if (id > 0) {
					result = serviceExam.deleteExam(id);
					if (result == 1) {
						processRequest(request, response);
					} else {
						request.setAttribute(error, sqlIssue);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					}
				} else {
					request.setAttribute(error, idNotfound);
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
