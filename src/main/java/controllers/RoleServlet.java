package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Role;
import services.RoleService;

@WebServlet(name = "roleServlet", urlPatterns = { 
		"/role-table", 
		"/role-add", 
		"/role-edit", 
		"/role-delete" 
})
public class RoleServlet extends HttpServlet {
	private RoleService roleService;

	@Override
	public void init() throws ServletException {
		roleService = new RoleService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/role-table": {
			List<Role> list = roleService.getAll();
			req.setAttribute("list", list);
			req.getRequestDispatcher("/views/role-table.jsp").forward(req, resp);
			break;
		}
		case "/role-add": {
			req.getRequestDispatcher("/views/role-add.jsp").forward(req, resp);
			break;
		}
		case "/role-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/role-table");
				return;
			}
			Role role = roleService.getById(id);
			if (role == null) {
				resp.sendRedirect(req.getContextPath() + "/role-table");
				return;
			}
			req.setAttribute("roleEdit", role);
			req.setAttribute("isEdit", true);
			req.getRequestDispatcher("/views/role-add.jsp").forward(req, resp);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();

		switch (path) {
		case "/role-add": {
			String nameRole = req.getParameter("nameRole");
			String description = req.getParameter("description");

			String error = roleService.create(nameRole, description);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/role-add?created=1");
				return;
			}
			req.setAttribute("error", error);
			req.getRequestDispatcher("/views/role-add.jsp").forward(req, resp);
			break;
		}
		case "/role-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/role-table");
				return;
			}
			String nameRole = req.getParameter("nameRole");
			String description = req.getParameter("description");

			Role role = new Role(id, nameRole, description);
			String error = roleService.update(role);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/role-table?updated=1");
				return;
			}
			req.setAttribute("error", error);
			req.setAttribute("isEdit", true);
			req.setAttribute("roleEdit", role);
			req.getRequestDispatcher("/views/role-add.jsp").forward(req, resp);
			break;
		}
		case "/role-delete": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			String error = roleService.deleteById(id);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/role-table?delete=1");
			} else {
				resp.sendRedirect(req.getContextPath() + "/role-table?err=1");
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}
