package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import services.AuthService;

@WebServlet(name = "authController", urlPatterns = { "/login", "/logout" })
public class AuthController extends HttpServlet {

	private AuthService authService;

	public AuthController() {
		authService = new AuthService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session = req.getSession(false);

		switch (path) {
		case "/login": {
			// co session thi khong vao duoc trang login
			if (session != null && session.getAttribute("currentUser") != null) {
				// cho vao trang chu
				resp.sendRedirect(req.getContextPath() + "/");
				return;
			}

			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			break;
		}
		case "/logout": {
			// xoa session va da ra login
			if (session != null) {
				// huyr toan bo session
				session.invalidate();
			}
			// ddieefu huong ve login
			resp.sendRedirect(req.getContextPath() + "/login");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		// neu như có session thì trả ra session hiện tại
		// không có thì trả null
		// ton tai nguyen cua server
		// Noi Giu session
		// 
		// cookie/ 
		HttpSession session = req.getSession(false);

		switch (path) {
		case "/login": {

			String email = req.getParameter("email");
			String password = req.getParameter("password");

			User user = authService.login(email, password);
			if (user == null) {
				req.setAttribute("err", "Email hoặc mật khẩu không chính xác");
				// show màn hình login
				req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
				return; // kết thúc
			}
			// đúng thì đổi hướng qua trang chủ
			// khong luu password trong session
			user.setPassword(null);
			session.setAttribute("currentUser", user);
			// luu sessionId: id , currentUser: user
			session.setMaxInactiveInterval(30 * 60);

			resp.sendRedirect(req.getContextPath() + "/");
			break;

		}
		case "/logout": {

			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}
