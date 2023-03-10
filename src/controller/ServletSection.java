package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Section;
import service.ServiceSection;

//@WebFilter(urlPatterns = {"/views/pages/sections", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/sections", "/views/errors/default_error" })
//@WebServlet("/views/pages/sections")
@WebServlet(name = "ServletSection", urlPatterns = "/views/pages/ServletSection")

public class ServletSection extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceSection serviceSection;

	public void init() {
		serviceSection = new ServiceSection();
	}

	public ServletSection() {
		super();
	}

	private boolean isOutOfRange(String str) {
		int len = str.length();
		if (len < 3 || len > 255) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isEmptyCbox(String input) {
		if (input != null && input.equals("Select")) {
			return true;
		} else {
			return false;
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("sections", serviceSection.getAllSections().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("sections.jsp");
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
					request.setAttribute("section", serviceSection.getSecById(id));
//					request.setAttribute("sectionStat", serviceSection.getSectionStat(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("section", serviceSection.getSecById(id));
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
			String sectionRoom, numStud, numSeat, rating, activeStatus, error, emptyFields, outOfRange, duplicateVal,
					idNotfound, sqlIssue;

			id = result = 0;
			sectionRoom = numStud = numSeat = rating = activeStatus = null;

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
					sectionRoom = request.getParameter("crSecRoom");
					numStud = request.getParameter("crSecStud");
					numSeat = request.getParameter("crSecSeat");
					rating = request.getParameter("crSecRate");
					activeStatus = request.getParameter("crSecStat");

					if (sectionRoom.isEmpty() || numStud.isEmpty() || numSeat.isEmpty() || isEmptyCbox(rating)
							|| isEmptyCbox(activeStatus)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						int numStud01 = Integer.parseInt(numStud);
						int numSeat01 = Integer.parseInt(numSeat);
						if (isOutOfRange(sectionRoom) || numStud01 > 200 || numSeat01 > 200) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceSection.checkDuplicateRoom(sectionRoom)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Section sec = new Section();
								sec.setSecRoom(sectionRoom);
								sec.setSecStud(numStud01);
								sec.setSecSeat(numSeat01);
								sec.setSecRate(rating);
								sec.setSecStat(activeStatus);
								result = serviceSection.addSection(sec);
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
					sectionRoom = request.getParameter("upSecRoom");
					numStud = request.getParameter("upSecStud");
					numSeat = request.getParameter("upSecSeat");
					rating = request.getParameter("upSecRate");
					activeStatus = request.getParameter("upSecStat");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (sectionRoom.isEmpty() || numStud.isEmpty() || numSeat.isEmpty() || isEmptyCbox(rating)
							|| isEmptyCbox(activeStatus)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						int numStud01 = Integer.parseInt(numStud);
						int numSeat01 = Integer.parseInt(numSeat);
						if (isOutOfRange(sectionRoom) || numStud01 > 200 || numSeat01 > 200) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceSection.checkDuplicateRoomOnEdit(id, sectionRoom) == false) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Section sec = new Section();
								sec.setSecId(id);
								sec.setSecRoom(sectionRoom);
								sec.setSecStud(numStud01);
								sec.setSecSeat(numSeat01);
								sec.setSecRate(rating);
								sec.setSecStat(activeStatus);
								result = serviceSection.updateSection(sec);
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
					result = serviceSection.deleteSection(id);
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
