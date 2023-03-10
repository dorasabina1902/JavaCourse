package controller.commonController;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "ServletHome")
@WebServlet(name = "ServletHome", urlPatterns = "/views/commonPages/ServletHome")
//@WebServlet({"/views/commonPages/ServletHome", "/views/commonPages/homepage.jsp"})
//@WebServlet(name = "ServletHome")
//@WebServlet("/")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletHome() {
		super();
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String btnLoginRequest = request.getParameter("homepageLoginParam");
		String btnRegisterRequest = request.getParameter("homepageRegisterParam");
		String btnViewRequest = request.getParameter("btnView01");
		String anchorHomepageRequest = request.getParameter("homepageAccessParam");
		String logoutParam = request.getParameter("logoutParam");
		String pageTitle = request.getParameter("pageTitle");

		String loginAsAdmin = request.getParameter("loginAsAdmin");

		try {
			if (btnLoginRequest != null) {

				request.setAttribute("homepageLoginForm", "selected");
				processRequest(request, response);

			} else if (btnRegisterRequest != null) {

				request.setAttribute("homepageRegisterForm", "selected");
				processRequest(request, response);

			} else if (btnViewRequest != null) {

				btnLoginRequest = btnRegisterRequest = anchorHomepageRequest = null;
				processRequest(request, response);

			} else if (anchorHomepageRequest != null) {

				request.setAttribute("homepageAccessChecker", "selected");
				request.setAttribute("pageTitle", pageTitle);
				request.setAttribute("loginAsAdmin", loginAsAdmin);
				processRequest(request, response);

			} else if (logoutParam != null) {

				request.setAttribute("logoutParam", "selected");
				processRequest(request, response);
			} else {

				processRequest(request, response);
			}
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
