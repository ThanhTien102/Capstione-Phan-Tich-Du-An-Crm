package dto;

import java.util.ArrayList;
import java.util.List;

import models.Task;
import models.User;

public class MemberProjectTasksDTO {
	private User user;
	private List<Task> notStartedTasks;
	private List<Task> inProgressTasks;
	private List<Task> completedTasks;

	public MemberProjectTasksDTO() {
		this.notStartedTasks = new ArrayList<>();
		this.inProgressTasks = new ArrayList<>();
		this.completedTasks = new ArrayList<>();
	}

	public MemberProjectTasksDTO(User user) {
		this.user = user;
		this.notStartedTasks = new ArrayList<>();
		this.inProgressTasks = new ArrayList<>();
		this.completedTasks = new ArrayList<>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Task> getNotStartedTasks() {
		return notStartedTasks;
	}

	public void setNotStartedTasks(List<Task> notStartedTasks) {
		this.notStartedTasks = notStartedTasks;
	}

	public List<Task> getInProgressTasks() {
		return inProgressTasks;
	}

	public void setInProgressTasks(List<Task> inProgressTasks) {
		this.inProgressTasks = inProgressTasks;
	}

	public List<Task> getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(List<Task> completedTasks) {
		this.completedTasks = completedTasks;
	}
}

