package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Department;
import service.ServiceDepartment;

//@WebFilter(urlPatterns = {"/views/pages/departments", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/departments", "/views/errors/default_error" })
//@WebServlet("/views/pages/departments")
@WebServlet(name = "ServletDepartment", urlPatterns = "/views/pages/ServletDepartment")

public class ServletDepartment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceDepartment serviceDep;

	public void init() {
		serviceDep = new ServiceDepartment();
	}

	public ServletDepartment() {
		super();
	}

	private boolean isOutOfRange(String str) {
		int len = str.length();
		if (len < 8 || len > 255) {
			return true;
		} else {
			return false;
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("departments", serviceDep.getAllDepartments().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("departments.jsp");
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
					request.setAttribute("department", serviceDep.getDepById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("department", serviceDep.getDepById(id));
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
			String name, tech, desc, error, emptyFields, outOfRange, duplicateVal, idNotfound, sqlIssue;

			id = result = 0;
			name = tech = desc = null;

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
					name = request.getParameter("crDepName");
					tech = request.getParameter("crDepTech");
					desc = request.getParameter("crDepDesc");

					if (name.isEmpty() || tech.isEmpty()) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						int techNum = Integer.parseInt(tech);
						if (isOutOfRange(name) || techNum > 20) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceDep.checkDuplicateDepName(name)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Department dep = new Department();
								dep.setDepName(name);
								dep.setDepTech(techNum);
								dep.setDepDesc(desc);
								result = serviceDep.addDepartment(dep);
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

				// User Input Variables
				name = request.getParameter("upDepName");
				tech = request.getParameter("upDepTech");
				desc = request.getParameter("upDepDesc");
				id = Integer.parseInt(request.getParameter("postUpdate"));

				if (name.isEmpty() || tech.isEmpty()) {
					request.setAttribute(error, emptyFields);
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				} else {
					int techNum = Integer.parseInt(tech);
					if (isOutOfRange(name) || techNum > 20) {
						request.setAttribute(error, outOfRange);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						if (serviceDep.checkDuplicateDepNameOnEdit(id, name) == false) {
							request.setAttribute(error, duplicateVal);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							Department dep = new Department();
							dep.setDepId(id);
							dep.setDepName(name);
							dep.setDepTech(techNum);
							dep.setDepDesc(desc);
							result = serviceDep.updateDepartment(dep);
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
			/*************************************************
			 ************************************* Delete Form
			 *************************************************/
			else if (request.getParameter("postDelete") != null) {
				id = Integer.parseInt(request.getParameter("postDelete"));
				if (id > 0) {
					result = serviceDep.deleteDepartment(id);
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
