package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StatusDAO;
import dto.TaskListDTO;
import dto.UserTaskDetailDTO;
import models.User;
import services.TaskService;

@WebServlet(name = "profileController", urlPatterns = {
		"/profile",
		"/profile-edit"
})
public class ProfileController extends HttpServlet {
	private TaskService taskService;
	private StatusDAO statusDAO;

	@Override
	public void init() throws ServletException {
		taskService = new TaskService();
		statusDAO = new StatusDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session = req.getSession(false);
		User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		switch (path) {
		case "/profile": {
			UserTaskDetailDTO detail = taskService.getUserTaskDetail(currentUser.getId());
			req.setAttribute("detail", detail);
			req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			break;
		}
		case "/profile-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/profile");
				return;
			}
			TaskListDTO task = taskService.getDTOById(id);
			if (task == null) {
				resp.sendRedirect(req.getContextPath() + "/profile");
				return;
			}
			req.setAttribute("task", task);
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/profile-edit.jsp").forward(req, resp);
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
		case "/profile-edit": {
			int id;
			int statusId;
			try {
				id = Integer.parseInt(req.getParameter("id"));
				statusId = Integer.parseInt(req.getParameter("statusId"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/profile");
				return;
			}

			String error = taskService.updateStatus(id, statusId);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/profile?updated=1");
				return;
			}
			req.setAttribute("error", error);
			req.setAttribute("task", taskService.getDTOById(id));
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/profile-edit.jsp").forward(req, resp);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}

