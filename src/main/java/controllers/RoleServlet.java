package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RoleDAO;
import models.Role;

@WebServlet("/role-table")
public class RoleServlet extends HttpServlet {
	private RoleDAO roleDAO;
	
	@Override
	public void init() throws ServletException {
		roleDAO = new RoleDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		// kiem tra xem co session currentUser thi cho di tiep 
	    // Chưa có session hoặc chưa đăng nhập
		if(session == null || session.getAttribute("currentUser") == null) {
			// chua co session hople da ra login
			resp.sendRedirect(req.getContextPath() +"/login");
			return;
		}
		
		List<Role> list  = roleDAO.getAll();
		
		// dua list vao jsp
		
		req.setAttribute("list",list);
		
		req.getRequestDispatcher("/views/role-table.jsp")
			.forward(req, resp);
	}
	

}
