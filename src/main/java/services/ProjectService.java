package services;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dto.MemberProjectTasksDTO;
import dto.TaskListDTO;
import dto.TaskStatsDTO;
import models.Project;
import models.Task;
import models.User;
import repositories.ProjectRepository;
import repositories.TaskRepository;

public class ProjectService {
	private final ProjectRepository projectRepository;
	private final TaskRepository taskRepository;

	public ProjectService() {
		this.projectRepository = new ProjectRepository();
		this.taskRepository = new TaskRepository();
	}

	public List<Project> getAll() {
		return projectRepository.getAll();
	}

	public Project getById(int id) {
		return projectRepository.getById(id);
	}

	public String create(String projectName, String startDateStr, String endDateStr) {
		if (projectName == null || projectName.trim().isEmpty()) {
			return "Tên dự án không được để trống";
		}

		Date startDate = parseDate(startDateStr);
		Date endDate = parseDate(endDateStr);

		Project project = new Project(projectName.trim(), startDate, endDate);
		boolean success = projectRepository.create(project);
		return success ? null : "Không thể tạo dự án";
	}

	public String update(int id, String projectName, String startDateStr, String endDateStr) {
		if (projectName == null || projectName.trim().isEmpty()) {
			return "Tên dự án không được để trống";
		}

		Date startDate = parseDate(startDateStr);
		Date endDate = parseDate(endDateStr);

		Project project = new Project(id, projectName.trim(), startDate, endDate);
		boolean success = projectRepository.update(project);
		return success ? null : "Không thể cập nhật dự án";
	}

	public String deleteById(int id) {
		try {
			boolean success = projectRepository.deleteById(id);
			return success ? null : "Không tìm thấy dự án";
		} catch (SQLException e) {
			return "Không thể xóa dự án vì có liên kết dữ liệu";
		}
	}

	public TaskStatsDTO getProjectStats(int projectId) {
		int notStarted = taskRepository.countByProjectIdAndStatusId(projectId, 1);
		int inProgress = taskRepository.countByProjectIdAndStatusId(projectId, 2);
		int completed = taskRepository.countByProjectIdAndStatusId(projectId, 3);
		return new TaskStatsDTO(notStarted, inProgress, completed);
	}

	public List<MemberProjectTasksDTO> getProjectMembersWithTasks(int projectId) {
		List<MemberProjectTasksDTO> list = new ArrayList<>();
		List<User> members = taskRepository.getUsersByProjectId(projectId);
		List<TaskListDTO> allProjectTasks = taskRepository.getByProjectId(projectId);

		for (User member : members) {
			MemberProjectTasksDTO memberDto = new MemberProjectTasksDTO(member);
			for (TaskListDTO taskDto : allProjectTasks) {
				if (taskDto.getUserId() == member.getId()) {
					Task t = new Task(taskDto.getId(), taskDto.getTaskName(), taskDto.getStartDate(),
							taskDto.getEndDate(), taskDto.getStatusId(), taskDto.getUserId(), taskDto.getProjectId());
					if (taskDto.getStatusId() == 1) {
						memberDto.getNotStartedTasks().add(t);
					} else if (taskDto.getStatusId() == 2) {
						memberDto.getInProgressTasks().add(t);
					} else if (taskDto.getStatusId() == 3) {
						memberDto.getCompletedTasks().add(t);
					}
				}
			}
			list.add(memberDto);
		}
		return list;
	}

	private Date parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		dateStr = dateStr.trim();
		// Try yyyy-MM-dd
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		// Try dd/MM/yyyy
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		// Try dd-MM-yyyy
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		return null;
	}
}

