package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoleDAO;
import dto.CreateUserDTO;
import models.User;
import services.UserService;



// xoá user bằng id .
// repository -> servce -> srvlet- 
@WebServlet(name = "userController", urlPatterns = { 
		"/user-table",
		"/user-add",
		"/user-edit",
		"/user-delete"})
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
			String keywords = req.getParameter("keywords");
			String pageParam = req.getParameter("page"); // tu url

			int size = 5;
			int page = 1;
			if (pageParam != null) {
				page = Integer.parseInt(pageParam);
			}
			if (keywords == null) {
				keywords = "";
			}

			try {
				int totalRow = userService.countUsers(keywords);
				// caafn du lieu cua user
				int totalPage = (totalRow + size - 1 )/ size;
				
				
				req.setAttribute("totalPage", totalPage);
				req.setAttribute("page", page);
				
				System.out.println(totalRow);
				System.out.println(totalPage);
				req.setAttribute("users", userService.getUserPaging(keywords, size, page));
				req.getRequestDispatcher("/views/user-table.jsp").forward(req, resp);
				
				
				
			} catch (SQLException e) {
		        throw new ServletException("Không tải được danh sách User", e);
			}

			break;
		}
		case "/user-add": {
			req.setAttribute("roles", new RoleDAO().getAll());
			req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
			break;
		}
		case "/user-edit": {
			// laasy ra id dang edit
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				// TODO: handle exception
				return;
			}
			// lay thong tin use tu DB
			try {
				User user = userService.findById(id);
				req.setAttribute("userEdit", user);
			}
			catch (Exception e) {
				// TODO: handle exception
				return;
			}
			
			// set gia tri cho jsp
			
			req.setAttribute("roles", new RoleDAO().getAll());
			req.setAttribute("isEdit", true);
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
			if (error == null) {
				// thanh cong
				req.setAttribute("error", null);

				resp.sendRedirect(req.getContextPath() + "/user-add?created=1");
				return;
			}
			// that bai
			req.setAttribute("error", error);
			req.setAttribute("roles", new RoleDAO().getAll());
			req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
			return;
		}
		
		case "/user-delete":{
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			}catch (NumberFormatException e) {
				// TODO: handle exception
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			try {
				String err = userService.deleteById(id);
				if(err == null) {
					resp.sendRedirect(
						req.getContextPath() + "/user-table?delete=1");
				}
				else {
					resp.sendRedirect(
						req.getContextPath() + "/user-table?err=1");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return;
		}
		case "/user-edit": {
			req.setCharacterEncoding("UTF-8");
			int roleId;
			int id;
			try {
				roleId= Integer.parseInt(req.getParameter("roleId"));
				id= Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				// TODO: handle exception
				req.setAttribute("error", "RoleId hoac Id sai dinh dang");
				return;
			}
			// edit casi gif
			User user = new User();
			user.setFullname(req.getParameter("fullname"));
			user.setEmail(req.getParameter("email"));
			user.setPhone(req.getParameter("phone"));
			user.setCountry(req.getParameter("country"));
			user.setRoleId(roleId);
			user.setId(id);
			
			try {
				String err = userService.update(user);
				if(err == null){// thanh cong
					resp.sendRedirect(req.getContextPath()+ "/user-table");
					return;
				}
				req.setAttribute("error", err);
				req.setAttribute("roles", new RoleDAO().getAll());
				req.setAttribute("isEdit", true); //
				req.setAttribute("userEdit", user); //
				

				req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);

			} catch (Exception e) {
				// TODO: handle exception
				return;
			}
			
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}
