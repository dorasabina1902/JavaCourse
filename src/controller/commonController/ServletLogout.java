package controller.commonController;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ServletLogout", urlPatterns = "/views/commonPages/ServletLogout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String logoutParam = request.getParameter("logoutParam");

			if (logoutParam != null) {
				HttpSession session = request.getSession();
				session.invalidate();
				request.setAttribute("logoutParam", "selected");
				RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception ex) {
			request.setAttribute("errors", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
