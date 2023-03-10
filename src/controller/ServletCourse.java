package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import model.Course;
import service.ServiceCourse;

//@WebFilter(urlPatterns = {"/views/pages/exams", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/exams", "/views/errors/default_error" })
//@WebServlet("/views/pages/exams")
@WebServlet(name = "ServletCourse", urlPatterns = "/views/pages/ServletCourse")

public class ServletCourse extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceCourse serviceCourse;

	public void init() {
		serviceCourse = new ServiceCourse();
	}

	public ServletCourse() {
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
		if (input.length() > 30 || input.length() < 3) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCredits(Double dbCredits) {
		ArrayList<Double> listDouble = new ArrayList<>();
		for (double i = 0.5; i < 3.5; i = i + 0.5) {
			listDouble.add(i);
		}
		if (listDouble.contains(dbCredits)) {
			return true;
		}
		return false;
	}

	public LocalDate getDateWithoutTimeFromString(String dateStr) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate ld = dtfOut.parseLocalDate(dateStr);
		return ld;
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceCourse", serviceCourse);
		request.setAttribute("courses", serviceCourse.getAllCourses().toArray());
		request.setAttribute("depNames", serviceCourse.selectDepNames().toArray());
		request.setAttribute("feeTypes", serviceCourse.selectFeeTypes().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("courses.jsp");
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
					request.setAttribute("course", serviceCourse.getCourseById(id));
					processRequest(request, response);
				}
			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("course", serviceCourse.getCourseById(id));
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
			String title, credits, remarks, depName, feeType, error, emptyFields, outOfRange, duplicateVal, idNotfound,
					sqlIssue;

			id = result = 0;
			title = credits = remarks = depName = remarks = depName = feeType = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {

				try {
					// User Input Variables
					title = request.getParameter("crCosTitle");
					credits = request.getParameter("crCosCrd");
					remarks = request.getParameter("crCosRmk");
					depName = request.getParameter("crCosDept");
					feeType = request.getParameter("crCosFee");

					if (title.isEmpty() || credits.isEmpty() || remarks.isEmpty() || isEmptyCbox(depName)
							|| isEmptyCbox(feeType)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						double dbCredits = Double.parseDouble(credits);
						int feeTypeId = serviceCourse.getFeeIdByFeeType(feeType);
						int depNameId = serviceCourse.getDepIdByDepName(depName);

						if (isOutOfRange(title) || !isCredits(dbCredits) || remarks.length() > 255) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceCourse.checkDuplicateCourse(title)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Course crs = new Course();
								crs.setCosTitle(title);
								crs.setCosCrd(dbCredits);
								crs.setCosRmk(remarks);
								crs.setCosDept(depNameId);
								crs.setCosFee(feeTypeId);
								result = serviceCourse.addCourse(crs);
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
					title = request.getParameter("upCosTitle");
					credits = request.getParameter("upCosCrd");
					remarks = request.getParameter("upCosRmk");
					depName = request.getParameter("upCosDept");
					feeType = request.getParameter("upCosFee");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (title.isEmpty() || credits.isEmpty() || remarks.isEmpty() || isEmptyCbox(depName)
							|| isEmptyCbox(feeType)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						double dbCredits = Double.parseDouble(credits);
						int feeTypeId = serviceCourse.getFeeIdByFeeType(feeType);
						int depNameId = serviceCourse.getDepIdByDepName(depName);

						if (isOutOfRange(title) || !isCredits(dbCredits) || remarks.length() > 255) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceCourse.checkDuplicateCourseOnEdit(id, title) == false) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Course crs = new Course();
								crs.setCosId(id);
								crs.setCosTitle(title);
								crs.setCosCrd(dbCredits);
								crs.setCosRmk(remarks);
								crs.setCosDept(depNameId);
								crs.setCosFee(feeTypeId);
								result = serviceCourse.updateCourse(crs);
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
					result = serviceCourse.deleteCourse(id);
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
		} catch (

		Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
