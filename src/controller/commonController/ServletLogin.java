package controller.commonController;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;
import model.Student;
import model.Teacher;
import service.commonService.ServiceLogin;

@WebServlet(name = "ServletLogin", urlPatterns = "/views/commonPages/ServletLogin")
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServiceLogin serviceLogin;

	public void init() {
		serviceLogin = new ServiceLogin();
	}

	public ServletLogin() {
		super();
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Press the login button on a particular role
		String btnStudentLogin = request.getParameter("btnStudentLogin");
		String btnTeacherLogin = request.getParameter("btnTeacherLogin");
		String btnAdminLogin = request.getParameter("btnAdminLogin");

		try {
			if (btnStudentLogin != null) {
				request.setAttribute("roleLoginParam", "student");
				processRequest(request, response);

			} else if (btnTeacherLogin != null) {
				request.setAttribute("roleLoginParam", "teacher");
				processRequest(request, response);

			} else if (btnAdminLogin != null) {
				request.setAttribute("roleLoginParam", "admin");
				processRequest(request, response);

			} else {
				request.setAttribute("homepageLoginForm", "selected");
				RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception ex) {
			request.setAttribute("error", "You do not have permission to access this page." + ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// User Input Variables
		String postLoginUser = request.getParameter("postLoginUser");
		String txtEmailAddress = request.getParameter("userEmailLogin");
		String txtPassword = request.getParameter("userPwdLogin");

		/*
		 * // Redirecting to a certain page (variables to achieve that) String
		 * redirectTowardsAdminFrom = request.getParameter("redirectTowardsAdmin");
		 */

		// Required Variables
		Student stdUser = new Student();
		Teacher tecUser = new Teacher();
		Admin admUser = new Admin();

		try {

			if (postLoginUser.equals("student")) {
				stdUser = serviceLogin.getStudentDetails(txtEmailAddress, txtPassword);

				if (stdUser == null) {
					request.setAttribute("error", "Incorrect email/password or the data entered doesnt exist");
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("student_id", stdUser.getStdId());
					session.setAttribute("first_name", stdUser.getStdFrst());
					session.setAttribute("middle_name", stdUser.getStdMid());
					session.setAttribute("last_name", stdUser.getStdLast());
					session.setAttribute("email", stdUser.getStdMail());
					session.setAttribute("password", stdUser.getStdPwd());
					session.setAttribute("address", stdUser.getStdAdd());
					session.setAttribute("gender", stdUser.getStdGen());
					session.setAttribute("date_of_birth", stdUser.getStdDob());
					session.setAttribute("contact", stdUser.getStdCont());
					session.setAttribute("attendance", stdUser.getStdAttd());
					session.setAttribute("image_file_name", stdUser.getStdImg());
					session.setAttribute("department_id", stdUser.getStdDept());
					session.setAttribute("grade_id", stdUser.getStdGrad());
					session.setAttribute("role_id", stdUser.getStdRole());
					session.setAttribute("sections_id", stdUser.getStdSect());

					RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
					dispatcher.forward(request, response);
				}

			} else if (postLoginUser.equals("teacher")) {
				tecUser = serviceLogin.getTeacherDetails(txtEmailAddress, txtPassword);

				if (tecUser == null) {
					request.setAttribute("error", "Incorrect email/password or the data entered doesnt exist");
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("teacher_id", tecUser.getTecId());
					session.setAttribute("first_name", tecUser.getTecFrst());
					session.setAttribute("middle_name", tecUser.getTecMid());
					session.setAttribute("last_name", tecUser.getTecLast());
					session.setAttribute("email", tecUser.getTecMail());
					session.setAttribute("password", tecUser.getTecPwd());
					session.setAttribute("address", tecUser.getTecAdd());
					session.setAttribute("gender", tecUser.getTecGen());
					session.setAttribute("date_of_birth", tecUser.getTecDob());
					session.setAttribute("contact", tecUser.getTecCont());
					session.setAttribute("hours", tecUser.getTecHrs());
					session.setAttribute("image_file_name", tecUser.getTecImg());
					session.setAttribute("department_id", tecUser.getTecDept());
					session.setAttribute("role_id", tecUser.getTecRole());

					RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
					dispatcher.forward(request, response);
				}

			} else if (postLoginUser.equals("admin")) {
				admUser = serviceLogin.getAdminDetails(txtEmailAddress, txtPassword);

				if (admUser == null) {
					request.setAttribute("error", "Incorrect email/password or the data entered doesnt exist");
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("admin_id", admUser.getAdmId());
					session.setAttribute("first_name", admUser.getAdmFrst());
					session.setAttribute("middle_name", admUser.getAdmMid());
					session.setAttribute("last_name", admUser.getAdmLast());
					session.setAttribute("email", admUser.getAdmMail());
					session.setAttribute("password", admUser.getAdmPwd());
					session.setAttribute("address", admUser.getAdmAdd());
					session.setAttribute("gender", admUser.getAdmGen());
					session.setAttribute("date_of_birth", admUser.getAdmDob());
					session.setAttribute("contact", admUser.getAdmCont());
					session.setAttribute("image_file_name", admUser.getAdmImg());
					session.setAttribute("role_id", admUser.getAdmRole());
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("welcome_page.jsp");
					dispatcher.forward(request, response);
				}
			}
		} catch (Exception ex) {
			request.setAttribute("error", "You do not have permission to access this page." + ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}
}
