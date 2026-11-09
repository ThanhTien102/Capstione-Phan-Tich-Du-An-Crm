package services;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import dto.TaskListDTO;
import dto.TaskStatsDTO;
import dto.UserTaskDetailDTO;
import models.Task;
import models.User;
import repositories.TaskRepository;
import repositories.UserRepository;

public class TaskService {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public TaskService() {
		this.taskRepository = new TaskRepository();
		this.userRepository = new UserRepository();
	}

	public List<TaskListDTO> getAll() {
		return taskRepository.getAll();
	}

	public Task getById(int id) {
		return taskRepository.getById(id);
	}

	public TaskListDTO getDTOById(int id) {
		return taskRepository.getDTOById(id);
	}

	public String create(String taskName, int projectId, int userId, String startDateStr, String endDateStr, int statusId) {
		if (taskName == null || taskName.trim().isEmpty()) {
			return "Tên công việc không được để trống";
		}
		if (projectId <= 0) {
			return "Vui lòng chọn dự án";
		}
		if (userId <= 0) {
			return "Vui lòng chọn người thực hiện";
		}

		Date startDate = parseDate(startDateStr);
		Date endDate = parseDate(endDateStr);

		Task task = new Task(taskName.trim(), startDate, endDate, statusId > 0 ? statusId : 1, userId, projectId);
		boolean success = taskRepository.create(task);
		return success ? null : "Không thể tạo công việc";
	}

	public String update(int id, String taskName, int projectId, int userId, String startDateStr, String endDateStr, int statusId) {
		if (taskName == null || taskName.trim().isEmpty()) {
			return "Tên công việc không được để trống";
		}
		if (projectId <= 0) {
			return "Vui lòng chọn dự án";
		}
		if (userId <= 0) {
			return "Vui lòng chọn người thực hiện";
		}

		Date startDate = parseDate(startDateStr);
		Date endDate = parseDate(endDateStr);

		Task task = new Task(id, taskName.trim(), startDate, endDate, statusId > 0 ? statusId : 1, userId, projectId);
		boolean success = taskRepository.update(task);
		return success ? null : "Không thể cập nhật công việc";
	}

	public String updateStatus(int taskId, int statusId) {
		if (taskId <= 0 || statusId <= 0) {
			return "Dữ liệu không hợp lệ";
		}
		boolean success = taskRepository.updateStatus(taskId, statusId);
		return success ? null : "Không thể cập nhật trạng thái";
	}

	public String deleteById(int id) {
		try {
			boolean success = taskRepository.deleteById(id);
			return success ? null : "Không tìm thấy công việc";
		} catch (SQLException e) {
			return "Không thể xóa công việc";
		}
	}

	public List<TaskListDTO> getByUserId(int userId) {
		return taskRepository.getByUserId(userId);
	}

	public List<TaskListDTO> getByProjectId(int projectId) {
		return taskRepository.getByProjectId(projectId);
	}

	public TaskStatsDTO getOverallStats() {
		int notStarted = taskRepository.countByStatusId(1);
		int inProgress = taskRepository.countByStatusId(2);
		int completed = taskRepository.countByStatusId(3);
		return new TaskStatsDTO(notStarted, inProgress, completed);
	}

	public TaskStatsDTO getUserStats(int userId) {
		int notStarted = taskRepository.countByUserIdAndStatusId(userId, 1);
		int inProgress = taskRepository.countByUserIdAndStatusId(userId, 2);
		int completed = taskRepository.countByUserIdAndStatusId(userId, 3);
		return new TaskStatsDTO(notStarted, inProgress, completed);
	}

	public UserTaskDetailDTO getUserTaskDetail(int userId) {
		try {
			User user = userRepository.getUserById(userId);
			if (user == null) return null;

			UserTaskDetailDTO detailDTO = new UserTaskDetailDTO();
			detailDTO.setUser(user);
			detailDTO.setStats(getUserStats(userId));

			List<TaskListDTO> tasks = taskRepository.getByUserId(userId);
			detailDTO.setAllTasks(tasks);

			for (TaskListDTO t : tasks) {
				if (t.getStatusId() == 1) {
					detailDTO.getNotStartedTasks().add(t);
				} else if (t.getStatusId() == 2) {
					detailDTO.getInProgressTasks().add(t);
				} else if (t.getStatusId() == 3) {
					detailDTO.getCompletedTasks().add(t);
				}
			}
			return detailDTO;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Date parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		dateStr = dateStr.trim();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			return new Date(sdf.parse(dateStr).getTime());
		} catch (Exception ignored) {}

		return null;
	}
}

