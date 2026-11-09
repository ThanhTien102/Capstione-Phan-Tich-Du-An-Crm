package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		List<Role> list  = roleDAO.getAll();
		
		// dua list vao jsp
		
		req.setAttribute("list",list);
		
		req.getRequestDispatcher("/views/role-table.jsp")
			.forward(req, resp);
	}
	

}
