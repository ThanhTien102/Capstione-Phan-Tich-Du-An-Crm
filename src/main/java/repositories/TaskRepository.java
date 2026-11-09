package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import dto.TaskListDTO;
import models.Task;
import models.User;

public class TaskRepository {

	public List<TaskListDTO> getAll() {
		List<TaskListDTO> list = new ArrayList<>();
		String sql = "SELECT t.id, t.task_name, t.start_date, t.end_date, t.status_id, "
				+ "COALESCE(s.name_status, 'Chưa xác định') AS status_name, "
				+ "t.project_id, COALESCE(p.name_project, '') AS project_name, "
				+ "t.user_id, COALESCE(u.fullname, '') AS user_name, u.email AS user_email "
				+ "FROM Task t "
				+ "LEFT JOIN Status s ON t.status_id = s.id "
				+ "LEFT JOIN Project p ON t.project_id = p.id "
				+ "LEFT JOIN User u ON t.user_id = u.id "
				+ "ORDER BY t.id DESC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(extractTaskListDTO(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Task getById(int id) {
		String sql = "SELECT * FROM Task WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String taskName = null;
					try {
						taskName = rs.getString("task_name");
					} catch (SQLException e) {
						taskName = rs.getString("name");
					}
					return new Task(
							rs.getInt("id"),
							taskName,
							rs.getDate("start_date"),
							rs.getDate("end_date"),
							rs.getInt("status_id"),
							rs.getInt("user_id"),
							rs.getInt("project_id")
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TaskListDTO getDTOById(int id) {
		String sql = "SELECT t.id, t.task_name, t.start_date, t.end_date, t.status_id, "
				+ "COALESCE(s.name_status, 'Chưa xác định') AS status_name, "
				+ "t.project_id, COALESCE(p.name_project, '') AS project_name, "
				+ "t.user_id, COALESCE(u.fullname, '') AS user_name, u.email AS user_email "
				+ "FROM Task t "
				+ "LEFT JOIN Status s ON t.status_id = s.id "
				+ "LEFT JOIN Project p ON t.project_id = p.id "
				+ "LEFT JOIN User u ON t.user_id = u.id "
				+ "WHERE t.id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractTaskListDTO(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean create(Task task) {
		String sql = "INSERT INTO Task (task_name, start_date, end_date, status_id, project_id, user_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, task.getTaskName());
			ps.setDate(2, task.getStartDate());
			ps.setDate(3, task.getEndDate());
			ps.setInt(4, task.getStatusId() > 0 ? task.getStatusId() : 1);
			ps.setInt(5, task.getProjectId());
			ps.setInt(6, task.getUserId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Task task) {
		String sql = "UPDATE Task SET task_name = ?, start_date = ?, end_date = ?, "
				+ "status_id = ?, project_id = ?, user_id = ? WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, task.getTaskName());
			ps.setDate(2, task.getStartDate());
			ps.setDate(3, task.getEndDate());
			ps.setInt(4, task.getStatusId());
			ps.setInt(5, task.getProjectId());
			ps.setInt(6, task.getUserId());
			ps.setInt(7, task.getId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStatus(int taskId, int statusId) {
		String sql = "UPDATE Task SET status_id = ? WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, statusId);
			ps.setInt(2, taskId);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteById(int id) throws SQLException {
		String sql = "DELETE FROM Task WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() == 1;
		}
	}

	public List<TaskListDTO> getByUserId(int userId) {
		List<TaskListDTO> list = new ArrayList<>();
		String sql = "SELECT t.id, t.task_name, t.start_date, t.end_date, t.status_id, "
				+ "COALESCE(s.name_status, 'Chưa xác định') AS status_name, "
				+ "t.project_id, COALESCE(p.name_project, '') AS project_name, "
				+ "t.user_id, COALESCE(u.fullname, '') AS user_name, u.email AS user_email "
				+ "FROM Task t "
				+ "LEFT JOIN Status s ON t.status_id = s.id "
				+ "LEFT JOIN Project p ON t.project_id = p.id "
				+ "LEFT JOIN User u ON t.user_id = u.id "
				+ "WHERE t.user_id = ? "
				+ "ORDER BY t.id DESC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(extractTaskListDTO(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TaskListDTO> getByProjectId(int projectId) {
		List<TaskListDTO> list = new ArrayList<>();
		String sql = "SELECT t.id, t.task_name, t.start_date, t.end_date, t.status_id, "
				+ "COALESCE(s.name_status, 'Chưa xác định') AS status_name, "
				+ "t.project_id, COALESCE(p.name_project, '') AS project_name, "
				+ "t.user_id, COALESCE(u.fullname, '') AS user_name, u.email AS user_email "
				+ "FROM Task t "
				+ "LEFT JOIN Status s ON t.status_id = s.id "
				+ "LEFT JOIN Project p ON t.project_id = p.id "
				+ "LEFT JOIN User u ON t.user_id = u.id "
				+ "WHERE t.project_id = ? "
				+ "ORDER BY t.id DESC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, projectId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(extractTaskListDTO(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<User> getUsersByProjectId(int projectId) {
		List<User> list = new ArrayList<>();
		String sql = "SELECT DISTINCT u.* FROM User u "
				+ "INNER JOIN Task t ON u.id = t.user_id "
				+ "WHERE t.project_id = ? "
				+ "ORDER BY u.fullname ASC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, projectId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new User(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("password"),
							rs.getString("fullname"),
							rs.getString("phone"),
							rs.getString("country"),
							rs.getInt("role_id")
					));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int countByStatusId(int statusId) {
		String sql = "SELECT COUNT(*) FROM Task WHERE status_id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, statusId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int countByUserIdAndStatusId(int userId, int statusId) {
		String sql = "SELECT COUNT(*) FROM Task WHERE user_id = ? AND status_id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setInt(2, statusId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int countByProjectIdAndStatusId(int projectId, int statusId) {
		String sql = "SELECT COUNT(*) FROM Task WHERE project_id = ? AND status_id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, projectId);
			ps.setInt(2, statusId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private TaskListDTO extractTaskListDTO(ResultSet rs) throws SQLException {
		TaskListDTO dto = new TaskListDTO();
		dto.setId(rs.getInt("id"));
		dto.setTaskName(rs.getString("task_name"));
		dto.setStartDate(rs.getDate("start_date"));
		dto.setEndDate(rs.getDate("end_date"));
		dto.setStatusId(rs.getInt("status_id"));
		dto.setStatusName(rs.getString("status_name"));
		dto.setProjectId(rs.getInt("project_id"));
		dto.setProjectName(rs.getString("project_name"));
		dto.setUserId(rs.getInt("user_id"));
		dto.setUserName(rs.getString("user_name"));
		dto.setUserEmail(rs.getString("user_email"));
		return dto;
	}
}

