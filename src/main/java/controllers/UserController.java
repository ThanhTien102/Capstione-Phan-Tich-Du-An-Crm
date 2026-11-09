package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoleDAO;
import dto.CreateUserDTO;
import services.UserService;

@WebServlet(name = "userController", urlPatterns = { "/user-table", "/user-add" })
public class UserController extends HttpServlet {
	private final UserService userService;

	public UserController() {
		userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();
		switch (path) {
		case "/user-table": {

			// caafn du lieu cua user
			req.setAttribute("users", userService.getAllUser());
			req.getRequestDispatcher("/views/user-table.jsp").forward(req, resp);
			break;
		}
		case "/user-add": {
			req.setAttribute("roles",
					new RoleDAO().getAll());
			req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {

		case "/user-add": {

			System.out.println("[CLICK ADD USER]");
			// lay thong tin input
			// CreateUserDto 
			CreateUserDTO dto = new CreateUserDTO();
			dto.setEmail(req.getParameter("email"));
			dto.setPassword(req.getParameter("password"));
			dto.setFullname(req.getParameter("fullname"));
			dto.setPhone(req.getParameter("phone"));
			dto.setCountry(req.getParameter("country"));
			dto.setRoleId(Integer.parseInt(req.getParameter("roleId")));
			
			// goi service
			String error = userService.create(dto);
			if(error == null) {
				// thanh cong
				req.setAttribute("error", null);
				
				resp.sendRedirect(req.getContextPath() 
						+ "/user-add?created=1");
				return;
			} 
			// that bai
			req.setAttribute("error", error );
			req.setAttribute("roles",
					new RoleDAO().getAll());
			req.getRequestDispatcher("/views/user-add.jsp")
			.forward(req, resp);
			return;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}
