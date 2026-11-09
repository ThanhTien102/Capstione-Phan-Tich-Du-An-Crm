package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StatusDAO;
import dto.TaskListDTO;
import models.Project;
import models.Task;
import models.User;
import repositories.UserRepository;
import services.ProjectService;
import services.TaskService;

@WebServlet(name = "taskController", urlPatterns = {
		"/task",
		"/task-add",
		"/task-edit",
		"/task-delete"
})
public class TaskController extends HttpServlet {
	private TaskService taskService;
	private ProjectService projectService;
	private UserRepository userRepository;
	private StatusDAO statusDAO;

	@Override
	public void init() throws ServletException {
		taskService = new TaskService();
		projectService = new ProjectService();
		userRepository = new UserRepository();
		statusDAO = new StatusDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/task": {
			List<TaskListDTO> list = taskService.getAll();
			req.setAttribute("tasks", list);
			req.getRequestDispatcher("/views/task.jsp").forward(req, resp);
			break;
		}
		case "/task-add": {
			req.setAttribute("projects", projectService.getAll());
			req.setAttribute("users", userRepository.getAll());
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
			break;
		}
		case "/task-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/task");
				return;
			}
			Task task = taskService.getById(id);
			if (task == null) {
				resp.sendRedirect(req.getContextPath() + "/task");
				return;
			}
			req.setAttribute("taskEdit", task);
			req.setAttribute("isEdit", true);
			req.setAttribute("projects", projectService.getAll());
			req.setAttribute("users", userRepository.getAll());
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
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
		case "/task-add": {
			String taskName = req.getParameter("taskName");
			int projectId = 0;
			int userId = 0;
			int statusId = 1;
			try {
				projectId = Integer.parseInt(req.getParameter("projectId"));
				userId = Integer.parseInt(req.getParameter("userId"));
				if (req.getParameter("statusId") != null) {
					statusId = Integer.parseInt(req.getParameter("statusId"));
				}
			} catch (Exception ignored) {}

			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			String error = taskService.create(taskName, projectId, userId, startDate, endDate, statusId);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/task-add?created=1");
				return;
			}
			req.setAttribute("error", error);
			req.setAttribute("projects", projectService.getAll());
			req.setAttribute("users", userRepository.getAll());
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
			break;
		}
		case "/task-edit": {
			int id;
			int projectId = 0;
			int userId = 0;
			int statusId = 1;
			try {
				id = Integer.parseInt(req.getParameter("id"));
				projectId = Integer.parseInt(req.getParameter("projectId"));
				userId = Integer.parseInt(req.getParameter("userId"));
				statusId = Integer.parseInt(req.getParameter("statusId"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/task");
				return;
			}
			String taskName = req.getParameter("taskName");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			String error = taskService.update(id, taskName, projectId, userId, startDate, endDate, statusId);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/task?updated=1");
				return;
			}
			Task task = new Task(id, taskName, null, null, statusId, userId, projectId);
			req.setAttribute("error", error);
			req.setAttribute("isEdit", true);
			req.setAttribute("taskEdit", task);
			req.setAttribute("projects", projectService.getAll());
			req.setAttribute("users", userRepository.getAll());
			req.setAttribute("statuses", statusDAO.getAll());
			req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
			break;
		}
		case "/task-delete": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			String error = taskService.deleteById(id);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/task?delete=1");
			} else {
				resp.sendRedirect(req.getContextPath() + "/task?err=1");
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}

