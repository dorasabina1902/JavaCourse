package controller.commonController;

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

import model.Admin;
import service.commonService.ServiceAdmin;

@WebServlet(name = "ServletAdmin", urlPatterns = "/views/commonPages/ServletAdmin")
@MultipartConfig(maxFileSize = 16177215)
public class ServletAdmin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "WebContent\\resources\\images";

	private ServiceAdmin serviceAdmin;

	public void init() {
		serviceAdmin = new ServiceAdmin();
	}

	public ServletAdmin() {
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

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("serviceAdmin", serviceAdmin);
		request.setAttribute("admins", serviceAdmin.getAllAdmins().toArray());
		request.setAttribute("roleNames", serviceAdmin.selectRoleNames().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("admins.jsp");
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String createParamId = request.getParameter("btnCreate");
		String editParamId = request.getParameter("btnEdit");
		String displayParamId = request.getParameter("btnDisplay");
		String viewParamId = request.getParameter("viewParam");
		String view01ParamId = request.getParameter("btnView01");
		String view02ParamId = request.getParameter("btnView02");

		String registerAdminRole = request.getParameter("btnRegister");
		String editAdminProfile = request.getParameter("editAdminProfile");
		String displayProfile = request.getParameter("displayProfile");

//		String showAdminLoginForm = request.getParameter("showAdminLoginFormParam");
//		String showWelcomePage = request.getParameter("showWelcomePage");
//		String displayAdminProfile = request.getParameter("displayAdminProfile");

		try {

			if (view01ParamId != null || view02ParamId != null) {
				viewParamId = createParamId = editParamId = displayParamId = null;
				RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
				dispatcher.forward(request, response);

			} else if (viewParamId != null) {
				request.setAttribute("viewParam", "selected");
				processRequest(request, response);

			} else if (createParamId != null) {
				request.setAttribute("createParam", "selected");
				processRequest(request, response);

			} else if (editParamId != null) {
				int id = Integer.parseInt(editParamId);
				if (id > 0) {
					request.setAttribute("editParam", "selected");
					request.setAttribute("admin", serviceAdmin.getAdminById(id));
					processRequest(request, response);
				}
			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);

				request.setAttribute("displayParam", "selected");
				request.setAttribute("admin", serviceAdmin.getAdminById(id));
				processRequest(request, response);

			} else if (registerAdminRole != null) {
				request.setAttribute("loginAsAdmin", "selected");
				request.setAttribute("registerAdminRole", "selected");
				request.setAttribute("createParam", "selected");
				processRequest(request, response);

			} else if (editAdminProfile != null) {
				request.setAttribute("admin", serviceAdmin.getAdminByContact(editAdminProfile));
				request.setAttribute("registerAdminRole", "selected");
				request.setAttribute("editParam", "selected");
				processRequest(request, response);

			} else if (displayProfile != null) {
				request.setAttribute("admin", serviceAdmin.getAdminByContact(displayProfile));
				request.setAttribute("registerAdminRole", "selected");
				request.setAttribute("displayParam", "selected");
				processRequest(request, response);

			} else {
				viewParamId = createParamId = editParamId = displayParamId = null;
				processRequest(request, response);
			}
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			/*************************************************************************************************
			 ****************************************************************************** Required Variables
			 ************************************************************************************************/
			int id, result;
			String firstName, middleName, lastName, email, password, address, gender, dateOfBirth, contact, fileName,
					roleName, savePath, error, emptyFields, outOfRange, dateOutOfRange, duplicateVal, idNotfound, sqlIssue,
					imageSizeTooLarge;
			Part profileImg;

			id = result = 0;
			firstName = middleName = lastName = email = password = address = gender = dateOfBirth = contact = fileName = roleName = savePath = null;
			profileImg = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 4 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";
			dateOutOfRange = "Date out of range! Insert date between 1930 and 2020.";
			imageSizeTooLarge = "Invalid Image Size! The size of image is too big or out of bounds.";
//		imageTypeInvalid = "Image type not supported! Please use one of these - jpg, jpeg or png.";

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
					// User Input Variables
					firstName = request.getParameter("rgAdmFrst");
					middleName = request.getParameter("rgAdmMid");
					lastName = request.getParameter("rgAdmLast");
					email = request.getParameter("rgAdmMail");
					password = request.getParameter("rgAdmPwd");
					address = request.getParameter("rgAdmAdd");
					gender = request.getParameter("rgAdmGen");
					dateOfBirth = request.getParameter("rgAdmDob");
					contact = request.getParameter("rgAdmCont");
					profileImg = request.getPart("rgAdmImg");
					roleName = request.getParameter("rgAdmRole");

					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || profileImg.equals(null)
							|| isEmptyCbox(roleName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();
						int roleId = serviceAdmin.getRoleIdByRoleName(roleName);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100 || contact.length() > 50
								|| profileImg.getSize() > 16177215) {
							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {

							if (myYear < 1930 || myYear > 2003 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceAdmin.checkDuplicateAdmin(firstName, contact, email)) {
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

									Admin adm = new Admin();
									adm.setAdmFrst(firstName);
									adm.setAdmMid(middleName);
									adm.setAdmLast(lastName);
									adm.setAdmMail(email);
									adm.setAdmPwd(password);
									adm.setAdmAdd(address);
									adm.setAdmGen(gender);
									adm.setAdmDob(myDate);
									adm.setAdmCont(contact);
									adm.setAdmImg(fileName);
									adm.setAdmRole(roleId);
									result = serviceAdmin.addAdmin(adm);

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
											session.setAttribute("admin_id", adm.getAdmId());
											session.setAttribute("first_name", adm.getAdmFrst());
											session.setAttribute("middle_name", adm.getAdmMid());
											session.setAttribute("last_name", adm.getAdmLast());
											session.setAttribute("email", adm.getAdmMail());
											session.setAttribute("password", adm.getAdmPwd());
											session.setAttribute("address", adm.getAdmAdd());
											session.setAttribute("gender", adm.getAdmGen());
											session.setAttribute("date_of_birth", adm.getAdmDob());
											session.setAttribute("contact", adm.getAdmCont());
											session.setAttribute("image_file_name", adm.getAdmImg());
											session.setAttribute("role_id", adm.getAdmRole());
											RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
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

			} else if (request.getParameter("postUpdate") != null) {
				try {
					// User Input Variables
					firstName = request.getParameter("rgAdmFrst");
					middleName = request.getParameter("rgAdmMid");
					lastName = request.getParameter("rgAdmLast");
					email = request.getParameter("rgAdmMail");
					password = request.getParameter("rgAdmPwd");
					address = request.getParameter("rgAdmAdd");
					gender = request.getParameter("rgAdmGen");
					dateOfBirth = request.getParameter("rgAdmDob");
					contact = request.getParameter("rgAdmCont");
					profileImg = request.getPart("rgAdmImg");
					roleName = request.getParameter("rgAdmRole");
					id = Integer.parseInt(request.getParameter("postUpdate"));

					if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()
							|| gender.isEmpty() || dateOfBirth.isEmpty() || contact.isEmpty() || profileImg.equals(null)
							|| isEmptyCbox(roleName)) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {

						firstName = firstName.replaceAll("\\s", "");
						middleName = middleName.replaceAll("\\s", "");
						lastName = lastName.replaceAll("\\s", "");

						LocalDate myDate = getDateWithoutTimeFromString(dateOfBirth);
						int myYear = myDate.getYear();
						int roleId = serviceAdmin.getRoleIdByRoleName(roleName);

						if (isOutOfRange(firstName) || middleName.length() > 30 || isOutOfRange(lastName) || email.length() > 60
								|| password.length() < 8 || password.length() > 255 || address.length() > 100 || contact.length() > 50
								|| profileImg.getSize() > 16177215) {
							request.setAttribute(error, outOfRange);
							request.setAttribute("error01", imageSizeTooLarge);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {

							if (myYear < 1930 || myYear > 2003 || myYear == 0) {
								request.setAttribute(error, dateOutOfRange);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								if (serviceAdmin.checkDuplicateAdminOnEdit(id, contact, email) == false) {
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

									Admin adm = new Admin();
									adm.setAdmId(id);
									adm.setAdmFrst(firstName);
									adm.setAdmMid(middleName);
									adm.setAdmLast(lastName);
									adm.setAdmMail(email);
									adm.setAdmPwd(password);
									adm.setAdmAdd(address);
									adm.setAdmGen(gender);
									adm.setAdmDob(myDate);
									adm.setAdmCont(contact);
									adm.setAdmImg(fileName);
									adm.setAdmRole(roleId);
									result = serviceAdmin.updateAdmin(adm);

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
											session.setAttribute("admin_id", adm.getAdmId());
											session.setAttribute("first_name", adm.getAdmFrst());
											session.setAttribute("middle_name", adm.getAdmMid());
											session.setAttribute("last_name", adm.getAdmLast());
											session.setAttribute("email", adm.getAdmMail());
											session.setAttribute("password", adm.getAdmPwd());
											session.setAttribute("address", adm.getAdmAdd());
											session.setAttribute("gender", adm.getAdmGen());
											session.setAttribute("date_of_birth", adm.getAdmDob());
											session.setAttribute("contact", adm.getAdmCont());
											session.setAttribute("image_file_name", adm.getAdmImg());
											session.setAttribute("role_id", adm.getAdmRole());
											RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
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
					System.out.println(ex.getMessage());
				}
			} else if (request.getParameter("postDelete") != null) {
				id = Integer.parseInt(request.getParameter("postDelete"));
				if (id > 0) {
					result = serviceAdmin.deleteAdmin(id);
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
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}
}
