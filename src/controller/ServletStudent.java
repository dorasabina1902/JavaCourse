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

import model.Student;
import service.ServiceStudent;

//@WebFilter(urlPatterns = {"/views/pages/students", "/views/errors/default_error"})
//@WebServlet({ "/views/pages/students", "/views/errors/default_error" })
//@WebServlet("/views/pages/students")
@WebServlet(name = "ServletStudent", urlPatterns = "/views/pages/ServletStudent")
@MultipartConfig(maxFileSize = 16177215)
/*
 * MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB maxFileSize =
 * 1024 * 1024 * 10, // 10MB maxRequestSize = 1024 * 1024 * 50) // 50MB
 */
public class ServletStudent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "WebContent\\resources\\images";

	private ServiceStudent serviceStudent;

	public void init() {
		serviceStudent = new ServiceStudent();
	}

	public ServletStudent() {
		super();
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 **************************************************************** Get the context path of your project
	 *****************************************************************************************************
	 ****************************************************************************************************/
	public String getURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ******************************************************************** Check if the select box is empty
	 *****************************************************************************************************
	 ****************************************************************************************************/
	public boolean isEmptyCbox(String input) {
		if (input != null && input.equals("Select")) {
			return true;
		} else {
			return false;
		}
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ******************************************************* Check if the length of string is out of range
	 *****************************************************************************************************
	 ****************************************************************************************************/
	public boolean isOutOfRange(String input) {
		if (input.length() > 30 || input.length() < 3) {
			return true;
		} else {
			return false;
		}
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ************************************************************************ Change to date from datetime
	 *****************************************************************************************************
	 ****************************************************************************************************/
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

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ***************************************************************** Common Request dispatcher for pages
	 *****************************************************************************************************
	 ****************************************************************************************************/
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceStudent", serviceStudent);
		request.setAttribute("students", serviceStudent.getAllStudents().toArray());
		request.setAttribute("depNames", serviceStudent.selectDepNames().toArray());
		request.setAttribute("gradeScores", serviceStudent.selectGradeNames().toArray());
		request.setAttribute("roleNames", serviceStudent.selectRoleNames().toArray());
		request.setAttribute("sectionRooms", serviceStudent.selectSectionNames().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("students.jsp");
		dispatcher.forward(request, response);
	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ************************************************************************************* HTTP Get method
	 *****************************************************************************************************
	 ****************************************************************************************************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String createParamId = request.getParameter("btnCreate");
			String editParamId = request.getParameter("btnEdit");
			String displayParamId = request.getParameter("btnDisplay");
			String view01ParamId = request.getParameter("btnView01");
			String view02ParamId = request.getParameter("btnView02");

			String registerStudentRole = request.getParameter("btnRegister");
			String editStudentProfile = request.getParameter("editStudentProfile");
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
					request.setAttribute("student", serviceStudent.getStudentById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("student", serviceStudent.getStudentById(id));
				processRequest(request, response);

			} else if (registerStudentRole != null) {
				request.setAttribute("registerStudentRole", "selected");
				request.setAttribute("createParam", "selected");
				processRequest(request, response);

			} else if (editStudentProfile != null) {
				request.setAttribute("student", serviceStudent.getStudentByContact(editStudentProfile));
				request.setAttribute("registerStudentRole", "selected");
				request.setAttribute("editParam", "selected");
				processRequest(request, response);

			} else if (displayProfile != null) {
				request.setAttribute("student", serviceStudent.getStudentByContact(displayProfile));
				request.setAttribute("registerStudentRole", "selected");
				request.setAttribute("displayParam", "selected");
				processRequest(request, response);

			} else {
				processRequest(request, response);
			}
		} catch (

		Exception ex) {
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}

	}

	/*****************************************************************************************************
	 *****************************************************************************************************
	 ************************************************************************************ HTTP Post method
	 *****************************************************************************************************
	 ****************************************************************************************************/
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			/*************************************************************************************************
			 ****************************************************************************** Required Variables
			 ************************************************************************************************/
			int id, result;
			String firstName, middleName, lastName, email, password, address, gender, dateOfBirth, contact, fileName,
					attendance, depName, gradeScore, roleName, sectionRoom, savePath, error, emptyFields, outOfRange,
					dateOutOfRange, duplicateVal, idNotfound, sqlIssue, imageSizeTooLarge;
			Part profileImg;

			id = result = 0;
			firstName = middleName = lastName = email = password = address = gender = dateOfBirth = contact = fileName = attendance = depName = gradeScore = roleName = sectionRoom = savePath = null;
			profileImg = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";
			dateOutOfRange = "Date out of range! Insert date between 1930 and 2020.";
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

			if (request.getParameter("postCreate") != null) {
				try {

					/*********************************************************************************************
					 ************************************************************************ User Input Variables
					 ********************************************************************************************/
					firstName = request.getParameter("crStdFrst");
					middleName = request.getParameter("crStdMid");
					lastName = request.getParameter("crStdLast");
					email = request.getParameter("crStdMail");
					password = request.getParameter("crStdPwd");
					address = request.getParameter("crStdAdd");
					gender = request.getParameter("crStdGen");
					dateOfBirth = request.getParameter("crStdDob");
					contact = request.getParameter("crStdCont");
					profileImg = request.getPart("crStdImg");
					attendance = request.getParameter("crStdAttd");
					depName = request.getParameter("crStdDept");
					gradeScore = request.getParameter("crStdGrad");
					roleName = request.getParameter("crStdRole");
					sectionRoom = request.getParameter("crStdSect");

					/*********************************************************************************************
					 ***************************************************************************** Form Validation
					 ********************************************************************************************/
					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || isEmptyCbox(attendance)
							|| profileImg.equals(null) || isEmptyCbox(depName) || isEmptyCbox(gradeScore) || isEmptyCbox(roleName)
							|| isEmptyCbox(sectionRoom)) {

						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);

					} else {

						/*******************************************************************************************
						 **************************************************************************** Image variable
						 ******************************************************************************************/

						/*******************************************************************************************
						 *************************************************************************** Other variables
						 ******************************************************************************************/
						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();

						int depId = serviceStudent.getDepIdByDepName(depName);
						int grdId = serviceStudent.getGradeIdByGrade(gradeScore);
						int roleId = serviceStudent.getRoleIdByRoleName(roleName);
						int secId = serviceStudent.getSectionIdByRoomNumber(sectionRoom);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100
								|| profileImg.getSize() > 16177215 || contact.length() > 50) {

							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);

						} else {

							if (myYear < 1930 || myYear > 2020 || myYear == 0) {

								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);

							} else {

								if (serviceStudent.checkDuplicateStudent(firstName, contact, email)) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);

								} else {

									/*********************************************************************************************
									 ************************************************************************ Image Upload Process
									 ********************************************************************************************/

									fileName = System.currentTimeMillis() + extractFileName(profileImg);
									System.out.println(imagePath + File.separator + fileName);

									/*********************************************************************************************
									 **************************************************************** Adding a student to database
									 ********************************************************************************************/
									Student std = new Student();
									std.setStdFrst(firstName);
									std.setStdMid(middleName);
									std.setStdLast(lastName);
									std.setStdMail(email);
									std.setStdPwd(password);
									std.setStdAdd(address);
									std.setStdGen(gender);
									std.setStdDob(myDate);
									std.setStdCont(contact);
									std.setStdImg(fileName);
									std.setStdAttd(attendance);
									std.setStdDept(depId);
									std.setStdGrad(grdId);
									std.setStdRole(roleId);
									std.setStdSect(secId);
									result = serviceStudent.addStudent(std);

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
											session.setAttribute("student_id", std.getStdId());
											session.setAttribute("first_name", std.getStdFrst());
											session.setAttribute("middle_name", std.getStdMid());
											session.setAttribute("last_name", std.getStdLast());
											session.setAttribute("email", std.getStdMail());
											session.setAttribute("password", std.getStdPwd());
											session.setAttribute("address", std.getStdAdd());
											session.setAttribute("gender", std.getStdGen());
											session.setAttribute("date_of_birth", std.getStdDob());
											session.setAttribute("contact", std.getStdCont());
											session.setAttribute("attendance", std.getStdAttd());
											session.setAttribute("image_file_name", std.getStdImg());
											session.setAttribute("department_id", std.getStdDept());
											session.setAttribute("role_id", std.getStdRole());
											session.setAttribute("grade_id", std.getStdGrad());
											session.setAttribute("section_id", std.getStdSect());

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
			/*************************************************************************************************
			 ******************************************************************************** Edit Post Method
			 ************************************************************************************************/
			else if (request.getParameter("postUpdate") != null) {

				try {
					/*********************************************************************************************
					 ************************************************************************ User Input Variables
					 ********************************************************************************************/
					firstName = request.getParameter("upStdFrst");
					middleName = request.getParameter("upStdMid");
					lastName = request.getParameter("upStdLast");
					email = request.getParameter("upStdMail");
					password = request.getParameter("upStdPwd");
					address = request.getParameter("upStdAdd");
					gender = request.getParameter("upStdGen");
					dateOfBirth = request.getParameter("upStdDob");
					contact = request.getParameter("upStdCont");
					profileImg = request.getPart("upStdImg");
					attendance = request.getParameter("upStdAttd");
					depName = request.getParameter("upStdDept");
					gradeScore = request.getParameter("upStdGrad");
					roleName = request.getParameter("upStdRole");
					sectionRoom = request.getParameter("upStdSect");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					/*********************************************************************************************
					 ***************************************************************************** Form Validation
					 ********************************************************************************************/
					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || profileImg.equals(null)
							|| attendance.isEmpty() || isEmptyCbox(depName) || isEmptyCbox(roleName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();

						int depId = serviceStudent.getDepIdByDepName(depName);
						int grdId = serviceStudent.getGradeIdByGrade(gradeScore);
						int roleId = serviceStudent.getRoleIdByRoleName(roleName);
						int secId = serviceStudent.getSectionIdByRoomNumber(sectionRoom);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100 || contact.length() > 50
								|| profileImg.getSize() > 16177215) {
							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {

							if (myYear < 1930 || myYear > 2020 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceStudent.checkDuplicateStudentOnEdit(id, contact, email) == false) {
									request.setAttribute(error, duplicateVal);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								} else {

									/*********************************************************************************************
									 ************************************************************************ Image Upload Process
									 ********************************************************************************************/

									fileName = System.currentTimeMillis() + extractFileName(profileImg);
									System.out.println(imagePath + File.separator + fileName);
									/*********************************************************************************************
									 **************************************************************** Updating the student to database
									 ********************************************************************************************/
									Student std = new Student();
									std.setStdId(id);
									std.setStdFrst(firstName);
									std.setStdMid(middleName);
									std.setStdLast(lastName);
									std.setStdMail(email);
									std.setStdPwd(password);
									std.setStdAdd(address);
									std.setStdGen(gender);
									std.setStdDob(myDate);
									std.setStdCont(contact);
									std.setStdImg(fileName);
									std.setStdAttd(attendance);
									std.setStdDept(depId);
									std.setStdGrad(grdId);
									std.setStdRole(roleId);
									std.setStdSect(secId);
									result = serviceStudent.updateStudent(std);
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
											session.setAttribute("student_id", std.getStdId());
											session.setAttribute("first_name", std.getStdFrst());
											session.setAttribute("middle_name", std.getStdMid());
											session.setAttribute("last_name", std.getStdLast());
											session.setAttribute("email", std.getStdMail());
											session.setAttribute("password", std.getStdPwd());
											session.setAttribute("address", std.getStdAdd());
											session.setAttribute("gender", std.getStdGen());
											session.setAttribute("date_of_birth", std.getStdDob());
											session.setAttribute("contact", std.getStdCont());
											session.setAttribute("attendance", std.getStdAttd());
											session.setAttribute("image_file_name", std.getStdImg());
											session.setAttribute("department_id", std.getStdDept());
											session.setAttribute("role_id", std.getStdRole());
											session.setAttribute("grade_id", std.getStdGrad());
											session.setAttribute("section_id", std.getStdSect());
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

			/*************************************************************************************************
			 ******************************************************************************* Delete Post Method
			 *************************************************************************************************/
			else if (request.getParameter("postDelete") != null) {
				id = Integer.parseInt(request.getParameter("postDelete"));
				if (id > 0) {
					result = serviceStudent.deleteStudent(id);
					if (result > 0) {
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
