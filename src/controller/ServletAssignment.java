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

import model.Assignment;
import service.ServiceAssignment;

//@WebFilter(urlPatterns = {"/views/pages/exams", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/exams", "/views/errors/default_error" })
//@WebServlet("/views/pages/exams")
@WebServlet(name = "ServletAssignment", urlPatterns = "/views/pages/ServletAssignment")

public class ServletAssignment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceAssignment serviceAssignment;

	public void init() {
		serviceAssignment = new ServiceAssignment();
	}

	public ServletAssignment() {
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

	public boolean checkAssignmentDates(LocalDate rlsDate, LocalDate subDate) {
		if (rlsDate.getYear() < 2000 || subDate.getYear() < 2000) {
			return false;
		} else {
			if (rlsDate.isBefore(subDate)) {
				return true;
			} else if (rlsDate.isAfter(subDate)) {
				return false;
			} else if (rlsDate.equals(subDate)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public LocalDate getDateWithoutTimeFromString(String dateStr) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate ld = dtfOut.parseLocalDate(dateStr);
		return ld;
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceAssignment", serviceAssignment);
		//request.setAttribute("assignments", serviceAssignment.getAllAssignments().toArray());
		request.setAttribute("depNames", serviceAssignment.selectDepNames().toArray());
		request.setAttribute("techNames", serviceAssignment.selectTechNames().toArray());
	//	RequestDispatcher dispatcher = request.getRequestDispatcher("assignments.jsp");
	//	dispatcher.forward(request, response);
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
					request.setAttribute("assignment", serviceAssignment.getAssignmentById(id));
					processRequest(request, response);
				}
			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("assignment", serviceAssignment.getAssignmentById(id));
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
			String title, releaseDate, deadline, skillLvl, depName, techName, error, emptyFields, outOfRange, duplicateVal,
					idNotfound, sqlIssue;

			id = result = 0;
			title = releaseDate = deadline = skillLvl = depName = techName = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit."
					+ "\n\n Except these, the release date and deadline should come after year 2000. Also, the release date should not come before deadline.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {

				try {
					// User Input Variables
					title = request.getParameter("crAsgTitle");
					releaseDate = request.getParameter("crAsgRls");
					deadline = request.getParameter("crAsgDead");
					skillLvl = request.getParameter("crAsgLvl");
					depName = request.getParameter("crAsgDept");
					techName = request.getParameter("crAsgTech");

					if (title.isEmpty() || releaseDate.isEmpty() || deadline.isEmpty() || isEmptyCbox(skillLvl)
							|| isEmptyCbox(depName) || isEmptyCbox(techName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						int depId = serviceAssignment.getDepIdByDepName(depName);
						int tecId = serviceAssignment.getTecIdByTechName(techName);

						LocalDate rlsDate = getDateWithoutTimeFromString(releaseDate);
						LocalDate subDate = getDateWithoutTimeFromString(deadline);

						if (title.length() > 100 || title.length() < 3 || checkAssignmentDates(rlsDate, subDate) == false) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceAssignment.checkDuplicateAssignment(title, rlsDate)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Assignment asg = new Assignment();
								asg.setAsgTitle(title);
								asg.setAsgRels(rlsDate);
								asg.setAsgDead(subDate);
								asg.setAsgLvl(skillLvl);
								asg.setAsgDept(depId);
								asg.setAsgTech(tecId);
								result = serviceAssignment.addAssignment(asg);
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
					title = request.getParameter("upAsgTitle");
					releaseDate = request.getParameter("upAsgRels");
					deadline = request.getParameter("upAsgDead");
					skillLvl = request.getParameter("upAsgLvl");
					depName = request.getParameter("upAsgDept");
					techName = request.getParameter("upAsgTech");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (title.isEmpty() || releaseDate.isEmpty() || deadline.isEmpty() || isEmptyCbox(skillLvl)
							|| isEmptyCbox(depName) || isEmptyCbox(techName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						int depId = serviceAssignment.getDepIdByDepName(depName);
						int tecId = serviceAssignment.getTecIdByTechName(techName);

						LocalDate rlsDate = getDateWithoutTimeFromString(releaseDate);
						LocalDate subDate = getDateWithoutTimeFromString(deadline);

						if (title.length() > 100 || title.length() < 3 || checkAssignmentDates(rlsDate, subDate) == false) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceAssignment.checkDuplicateAssignmentOnEdit(id, title, rlsDate) == false) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Assignment asg = new Assignment();
								asg.setAsgId(id);
								asg.setAsgTitle(title);
								asg.setAsgRels(rlsDate);
								asg.setAsgDead(subDate);
								asg.setAsgLvl(skillLvl);
								asg.setAsgDept(depId);
								asg.setAsgTech(tecId);
								result = serviceAssignment.updateAssignment(asg);
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
					result = serviceAssignment.deleteAssignment(id);
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
