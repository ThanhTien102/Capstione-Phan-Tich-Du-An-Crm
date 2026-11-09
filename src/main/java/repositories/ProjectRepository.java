package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Project;

public class ProjectRepository {

	public List<Project> getAll() {
		List<Project> list = new ArrayList<>();
		String sql = "SELECT * FROM Project ORDER BY id DESC";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(extractProject(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Project getById(int id) {
		String sql = "SELECT * FROM Project WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractProject(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean create(Project project) {
		// Try name_project first, if it fails try project_name or name
		String sql = "INSERT INTO Project (name_project, start_date, end_date) VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, project.getProjectName());
			ps.setDate(2, project.getStartDate());
			ps.setDate(3, project.getEndDate());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// Fallback if column is named project_name or name
			String fallbackSql = "INSERT INTO Project (project_name, start_date, end_date) VALUES (?, ?, ?)";
			try (Connection conn = DBConnection.getConnection();
					PreparedStatement ps = conn.prepareStatement(fallbackSql)) {
				ps.setString(1, project.getProjectName());
				ps.setDate(2, project.getStartDate());
				ps.setDate(3, project.getEndDate());
				return ps.executeUpdate() == 1;
			} catch (SQLException e2) {
				e2.printStackTrace();
				return false;
			}
		}
	}

	public boolean update(Project project) {
		String sql = "UPDATE Project SET name_project = ?, start_date = ?, end_date = ? WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, project.getProjectName());
			ps.setDate(2, project.getStartDate());
			ps.setDate(3, project.getEndDate());
			ps.setInt(4, project.getId());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			String fallbackSql = "UPDATE Project SET project_name = ?, start_date = ?, end_date = ? WHERE id = ?";
			try (Connection conn = DBConnection.getConnection();
					PreparedStatement ps = conn.prepareStatement(fallbackSql)) {
				ps.setString(1, project.getProjectName());
				ps.setDate(2, project.getStartDate());
				ps.setDate(3, project.getEndDate());
				ps.setInt(4, project.getId());
				return ps.executeUpdate() == 1;
			} catch (SQLException e2) {
				e2.printStackTrace();
				return false;
			}
		}
	}

	public boolean deleteById(int id) throws SQLException {
		String sql = "DELETE FROM Project WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() == 1;
		}
	}

	private Project extractProject(ResultSet rs) throws SQLException {
		String projectName = null;
		try {
			projectName = rs.getString("name_project");
		} catch (SQLException e) {
			try {
				projectName = rs.getString("project_name");
			} catch (SQLException e1) {
				projectName = rs.getString("name");
			}
		}
		return new Project(
				rs.getInt("id"),
				projectName,
				rs.getDate("start_date"),
				rs.getDate("end_date")
		);
	}
}

