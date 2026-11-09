package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.MemberProjectTasksDTO;
import dto.TaskStatsDTO;
import models.Project;
import services.ProjectService;

@WebServlet(name = "projectController", urlPatterns = {
		"/groupwork",
		"/groupwork-add",
		"/groupwork-edit",
		"/groupwork-delete",
		"/groupwork-details"
})
public class ProjectController extends HttpServlet {
	private ProjectService projectService;

	@Override
	public void init() throws ServletException {
		projectService = new ProjectService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/groupwork": {
			List<Project> list = projectService.getAll();
			req.setAttribute("projects", list);
			req.getRequestDispatcher("/views/groupwork.jsp").forward(req, resp);
			break;
		}
		case "/groupwork-add": {
			req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
			break;
		}
		case "/groupwork-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			Project project = projectService.getById(id);
			if (project == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			req.setAttribute("projectEdit", project);
			req.setAttribute("isEdit", true);
			req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
			break;
		}
		case "/groupwork-details": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			Project project = projectService.getById(id);
			if (project == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			TaskStatsDTO stats = projectService.getProjectStats(id);
			List<MemberProjectTasksDTO> membersWithTasks = projectService.getProjectMembersWithTasks(id);

			req.setAttribute("project", project);
			req.setAttribute("stats", stats);
			req.setAttribute("membersWithTasks", membersWithTasks);
			req.getRequestDispatcher("/views/groupwork-details.jsp").forward(req, resp);
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
		case "/groupwork-add": {
			String projectName = req.getParameter("projectName");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			String error = projectService.create(projectName, startDate, endDate);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork-add?created=1");
				return;
			}
			req.setAttribute("error", error);
			req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
			break;
		}
		case "/groupwork-edit": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			String projectName = req.getParameter("projectName");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			String error = projectService.update(id, projectName, startDate, endDate);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork?updated=1");
				return;
			}
			Project project = new Project(id, projectName, null, null);
			req.setAttribute("error", error);
			req.setAttribute("isEdit", true);
			req.setAttribute("projectEdit", project);
			req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
			break;
		}
		case "/groupwork-delete": {
			int id;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			String error = projectService.deleteById(id);
			if (error == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork?delete=1");
			} else {
				resp.sendRedirect(req.getContextPath() + "/groupwork?err=1");
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}

