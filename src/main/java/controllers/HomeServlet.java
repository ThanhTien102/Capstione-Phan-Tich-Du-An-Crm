package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.TaskStatsDTO;
import services.TaskService;

@WebServlet("")
public class HomeServlet extends HttpServlet {
	private TaskService taskService;

	@Override
	public void init() throws ServletException {
		taskService = new TaskService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TaskStatsDTO stats = taskService.getOverallStats();
		req.setAttribute("stats", stats);
		req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
}
