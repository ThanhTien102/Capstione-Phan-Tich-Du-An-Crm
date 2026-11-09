package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "errorController", urlPatterns = { "/blank", "/403", "/404" })
public class ErrorController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/blank":
			req.getRequestDispatcher("/views/blank.jsp").forward(req, resp);
			break;
		case "/403":
		case "/404":
		default:
			req.getRequestDispatcher("/views/403.jsp").forward(req, resp);
			break;
		}
	}
}

