package dto;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserTaskDetailDTO {
	private User user;
	private TaskStatsDTO stats;
	private List<TaskListDTO> notStartedTasks;
	private List<TaskListDTO> inProgressTasks;
	private List<TaskListDTO> completedTasks;
	private List<TaskListDTO> allTasks;

	public UserTaskDetailDTO() {
		this.notStartedTasks = new ArrayList<>();
		this.inProgressTasks = new ArrayList<>();
		this.completedTasks = new ArrayList<>();
		this.allTasks = new ArrayList<>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TaskStatsDTO getStats() {
		return stats;
	}

	public void setStats(TaskStatsDTO stats) {
		this.stats = stats;
	}

	public List<TaskListDTO> getNotStartedTasks() {
		return notStartedTasks;
	}

	public void setNotStartedTasks(List<TaskListDTO> notStartedTasks) {
		this.notStartedTasks = notStartedTasks;
	}

	public List<TaskListDTO> getInProgressTasks() {
		return inProgressTasks;
	}

	public void setInProgressTasks(List<TaskListDTO> inProgressTasks) {
		this.inProgressTasks = inProgressTasks;
	}

	public List<TaskListDTO> getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(List<TaskListDTO> completedTasks) {
		this.completedTasks = completedTasks;
	}

	public List<TaskListDTO> getAllTasks() {
		return allTasks;
	}

	public void setAllTasks(List<TaskListDTO> allTasks) {
		this.allTasks = allTasks;
	}
}

