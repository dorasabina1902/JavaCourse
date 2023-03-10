package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Grade;
import service.ServiceGrade;

//@WebFilter(urlPatterns = {"/views/pages/grades", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/grades", "/views/errors/default_error" })
//@WebServlet("/views/pages/grades")
@WebServlet(name = "ServletGrade", urlPatterns = "/views/pages/ServletGrade")

public class ServletGrade extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceGrade serviceGrade;

	public void init() {
		serviceGrade = new ServiceGrade();
	}

	public ServletGrade() {
		super();
	}

	private boolean isOutOfRange(String str) {
		int len = str.length();
		if (len > 255) {
			return true;
		} else {
			return false;
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("grades", serviceGrade.getAllGrades().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("grades.jsp");
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
					request.setAttribute("grade", serviceGrade.getGradeById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("grade", serviceGrade.getGradeById(id));
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
			String grade, percentage, remarks, error, emptyFields, outOfRange, duplicateVal, idNotfound, sqlIssue;

			id = result = 0;
			grade = percentage = remarks = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 8 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {

				try {
					// User Input Variables
					grade = request.getParameter("crGrdStd");
					percentage = request.getParameter("crGrdPer");
					remarks = request.getParameter("crGrdRmk");

					if (grade.isEmpty() || percentage.isEmpty()) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						double percentage01 = Double.parseDouble(percentage);
						if (isOutOfRange(remarks) || percentage01 > 100 || percentage01 < 0) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceGrade.checkDuplicateStdGrade(grade)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Grade grd = new Grade();
								grd.setGrdStd(grade);
								grd.setGrdPer(percentage01);
								grd.setGrdRmk(remarks);
								result = serviceGrade.addGrade(grd);
								if (result == 1) {
									processRequest(request, response);
								} else {
									request.setAttribute(error, sqlIssue);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
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
					grade = request.getParameter("upGrdStd");
					percentage = request.getParameter("upGrdPer");
					remarks = request.getParameter("upGrdRmk");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (grade.isEmpty() || percentage.isEmpty()) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						double percentage01 = Double.parseDouble(percentage);
						if (isOutOfRange(remarks) || percentage01 > 100 || percentage01 < 0) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceGrade.checkDuplicateStdGradeOnEdit(id, grade) == false) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Grade grd = new Grade();
								grd.setGrdId(id);
								grd.setGrdStd(grade);
								grd.setGrdPer(percentage01);
								grd.setGrdRmk(remarks);
								result = serviceGrade.updateGrade(grd);
								if (result == 1) {
									processRequest(request, response);
								} else {
									request.setAttribute(error, sqlIssue);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
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
					result = serviceGrade.deleteGrade(id);
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
