package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import model.Teacher;
import service.ServiceTeacher;

//@WebFilter(urlPatterns = {"/views/pages/teachers", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/teachers", "/views/errors/default_error" })
//@WebServlet("/views/pages/teachers")
@WebServlet(name = "ServletTeacher", urlPatterns = "/views/pages/ServletTeacher")
@MultipartConfig(maxFileSize = 16177215)
public class ServletTeacher extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "WebContent\\resources\\images";

	private ServiceTeacher serviceTeacher;

	public void init() {
		serviceTeacher = new ServiceTeacher();
	}

	public ServletTeacher() {
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

	public LocalDate getDateWithoutTimeFromString(String dateStr) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate ld = dtfOut.parseLocalDate(dateStr);
		return ld;
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ********************************************* Extracts file name from HTTP header content-disposition
	 *****************************************************************************************************
	 ****************************************************************************************************/
	public String extractFileName(Part part) {

		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");

		for (String str : items) {
			if (str.trim().startsWith("filename")) {
				return str.substring(str.indexOf("=") + 2, str.length() - 1);
			}
		}
		return "";
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceTeacher", serviceTeacher);
		request.setAttribute("teachers", serviceTeacher.getAllTeachers().toArray());
		request.setAttribute("depNames", serviceTeacher.selectDepNames().toArray());
		request.setAttribute("roleNames", serviceTeacher.selectRoleNames().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("teachers.jsp");
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

			String registerTeacherRole = request.getParameter("btnRegister");
			String editTeacherProfile = request.getParameter("editTeacherProfile");
			String displayProfile = request.getParameter("displayProfile");

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
					request.setAttribute("teacher", serviceTeacher.getTeacherById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("teacher", serviceTeacher.getTeacherById(id));
				processRequest(request, response);

			} else if (registerTeacherRole != null) {
				request.setAttribute("registerTeacherRole", "selected");
				request.setAttribute("createParam", "selected");
				processRequest(request, response);

			} else if (editTeacherProfile != null) {
				request.setAttribute("teacher", serviceTeacher.getTeacherByContact(editTeacherProfile));
				request.setAttribute("registerTeacherRole", "selected");
				request.setAttribute("editParam", "selected");
				processRequest(request, response);

			} else if (displayProfile != null) {
				request.setAttribute("teacher", serviceTeacher.getTeacherByContact(displayProfile));
				request.setAttribute("registerTeacherRole", "selected");
				request.setAttribute("displayParam", "selected");
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
			String firstName, middleName, lastName, email, password, address, gender, dateOfBirth, contact, fileName,
					savePath, hours, depName, roleName, error, emptyFields, outOfRange, dateOutOfRange, duplicateVal, idNotfound,
					sqlIssue, imageSizeTooLarge;
			Part profileImg;

			id = result = 0;
			firstName = middleName = lastName = email = password = address = gender = dateOfBirth = contact = fileName = savePath = hours = depName = roleName = null;
			profileImg = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";
			dateOutOfRange = "Date out of range! Insert date between 1930 and 2003.";
			imageSizeTooLarge = "Invalid Image Size! The size of image is too big or out of bounds.";
//			imageTypeInvalid = "Image type not supported! Please use one of these - jpg, jpeg or png.";

			savePath = request.getServletContext().getRealPath("/");
			System.out.println(savePath);

			int firstDot = savePath.indexOf(".");
			int secondLast = savePath.length() - 2;

			String fn = savePath.substring(0, firstDot);
			String ln = savePath.substring(savePath.lastIndexOf("\\", secondLast) + 1);

			String imagePath = fn + ln + SAVE_DIR;
			System.out.println(imagePath);

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {

				try {
					// User Input Variables
					firstName = request.getParameter("crTecFrst");
					middleName = request.getParameter("crTecMid");
					lastName = request.getParameter("crTecLast");
					email = request.getParameter("crTecMail");
					password = request.getParameter("crTecPwd");
					address = request.getParameter("crTecAdd");
					gender = request.getParameter("crTecGen");
					dateOfBirth = request.getParameter("crTecDob");
					contact = request.getParameter("crTecCont");
					profileImg = request.getPart("crTecImg");
					hours = request.getParameter("crTecHrs");
					depName = request.getParameter("crTecDept");
					roleName = request.getParameter("crTecRole");

					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || profileImg.equals(null)
							|| hours.isEmpty() || isEmptyCbox(depName) || isEmptyCbox(roleName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						int hoursNum = Integer.parseInt(hours);
						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();
						int depId = serviceTeacher.getDepIdByDepName(depName);
						int roleId = serviceTeacher.getRoleIdByRoleName(roleName);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100 || contact.length() > 50
								|| profileImg.getSize() > 16177215 || hoursNum > 10) {
							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {

							if (myYear < 1930 || myYear > 2003 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceTeacher.checkDuplicateTeacher(firstName, contact, email)) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								} else {

									/*********************************************************************************************
									 ************************************************************************ Image Upload Process
									 ********************************************************************************************/

									fileName = System.currentTimeMillis() + extractFileName(profileImg);
									System.out.println(imagePath + File.separator + fileName);

									/*********************************************************************************************
									 **************************************************************** Adding a teacher to database
									 ********************************************************************************************/

									Teacher tec = new Teacher();
									tec.setTecFrst(firstName);
									tec.setTecMid(middleName);
									tec.setTecLast(lastName);
									tec.setTecMail(email);
									tec.setTecPwd(password);
									tec.setTecAdd(address);
									tec.setTecGen(gender);
									tec.setTecDob(myDate);
									tec.setTecCont(contact);
									tec.setTecImg(fileName);
									tec.setTecHrs(hoursNum);
									tec.setTecDept(depId);
									tec.setTecRole(roleId);
									result = serviceTeacher.addTeacher(tec);

									if (result == 1) {
										File fileSaveDir = new File(savePath);

										if (!fileSaveDir.exists()) {
											fileSaveDir.mkdir();
										}
										profileImg.write(imagePath + File.separator + fileName);
										

										if (request.getParameter("comingFromRegisterForm") == null) {
											processRequest(request, response);
										} else {
											HttpSession session = request.getSession();
											session.setAttribute("teacher_id", tec.getTecId());
											session.setAttribute("first_name", tec.getTecFrst());
											session.setAttribute("middle_name", tec.getTecMid());
											session.setAttribute("last_name", tec.getTecLast());
											session.setAttribute("email", tec.getTecMail());
											session.setAttribute("password", tec.getTecPwd());
											session.setAttribute("address", tec.getTecAdd());
											session.setAttribute("gender", tec.getTecGen());
											session.setAttribute("date_of_birth", tec.getTecDob());
											session.setAttribute("contact", tec.getTecCont());
											session.setAttribute("hours", tec.getTecHrs());
											session.setAttribute("image_file_name", tec.getTecImg());
											session.setAttribute("department_id", tec.getTecDept());
											session.setAttribute("role_id", tec.getTecRole());
											RequestDispatcher dispatcher = request.getRequestDispatcher("../commonPages/welcome_page.jsp");
											dispatcher.forward(request, response);
										}

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
					firstName = request.getParameter("upTecFrst");
					middleName = request.getParameter("upTecMid");
					lastName = request.getParameter("upTecLast");
					email = request.getParameter("upTecMail");
					password = request.getParameter("upTecPwd");
					address = request.getParameter("upTecAdd");
					gender = request.getParameter("upTecGen");
					dateOfBirth = request.getParameter("upTecDob");
					contact = request.getParameter("upTecCont");
					profileImg = request.getPart("upTecImg");
					hours = request.getParameter("upTecHrs");
					depName = request.getParameter("upTecDept");
					roleName = request.getParameter("upTecRole");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || profileImg.equals(null)
							|| hours.isEmpty() || isEmptyCbox(depName) || isEmptyCbox(roleName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						int hoursNum = Integer.parseInt(hours);
						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();
						int depId = serviceTeacher.getDepIdByDepName(depName);
						int roleId = serviceTeacher.getRoleIdByRoleName(roleName);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100 || contact.length() > 50
								|| hoursNum > 10 || profileImg.getSize() > 16177215) {
							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {

							if (myYear < 1930 || myYear > 2003 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceTeacher.checkDuplicateTeacherOnEdit(id, contact, email) == false) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								} else {

									/*********************************************************************************************
									 ************************************************************************ Image Upload Process
									 ********************************************************************************************/

									fileName = System.currentTimeMillis() + extractFileName(profileImg);
									System.out.println(imagePath + File.separator + fileName);

									/*********************************************************************************************
									 ************************************************************ Updating the teacher to database
									 ********************************************************************************************/

									Teacher tec = new Teacher();
									tec.setTecId(id);
									tec.setTecFrst(firstName);
									tec.setTecMid(middleName);
									tec.setTecLast(lastName);
									tec.setTecMail(email);
									tec.setTecPwd(password);
									tec.setTecAdd(address);
									tec.setTecGen(gender);
									tec.setTecDob(myDate);
									tec.setTecCont(contact);
									tec.setTecImg(fileName);
									tec.setTecHrs(hoursNum);
									tec.setTecDept(depId);
									tec.setTecRole(roleId);
									result = serviceTeacher.updateTeacher(tec);

									if (result == 1) {
										File fileSaveDir = new File(savePath);

										if (!fileSaveDir.exists()) {
											fileSaveDir.mkdir();
										}
										profileImg.write(imagePath + File.separator + fileName);

										if (request.getParameter("comingFromNavbarAccount") == null) {
											processRequest(request, response);
										} else {
											HttpSession session = request.getSession();
											session.setAttribute("teacher_id", tec.getTecId());
											session.setAttribute("first_name", tec.getTecFrst());
											session.setAttribute("middle_name", tec.getTecMid());
											session.setAttribute("last_name", tec.getTecLast());
											session.setAttribute("email", tec.getTecMail());
											session.setAttribute("password", tec.getTecPwd());
											session.setAttribute("address", tec.getTecAdd());
											session.setAttribute("gender", tec.getTecGen());
											session.setAttribute("date_of_birth", tec.getTecDob());
											session.setAttribute("contact", tec.getTecCont());
											session.setAttribute("hours", tec.getTecHrs());
											session.setAttribute("image_file_name", tec.getTecImg());
											session.setAttribute("department_id", tec.getTecDept());
											session.setAttribute("role_id", tec.getTecRole());
											RequestDispatcher dispatcher = request.getRequestDispatcher("../commonPages/welcome_page.jsp");
											dispatcher.forward(request, response);
										}

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
					result = serviceTeacher.deleteTeacher(id);
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
