package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Role;

public class RoleDAO {

	public List<Role> getAll() {
		List<Role> roles = new ArrayList<Role>();

		String sql = "SELECT * FROM Role ORDER BY id ASC";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				roles.add(new Role(rs.getInt("id"), rs.getString("name_role"), rs.getString("description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	public Role getById(int id) {
		String sql = "SELECT * FROM Role WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Role(rs.getInt("id"), rs.getString("name_role"), rs.getString("description"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean create(Role role) {
		String sql = "INSERT INTO Role (name_role, description) VALUES (?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, role.getNameRole());
			ps.setString(2, role.getDescription());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Role role) {
		String sql = "UPDATE Role SET name_role = ?, description = ? WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, role.getNameRole());
			ps.setString(2, role.getDescription());
			ps.setInt(3, role.getId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteById(int id) throws SQLException {
		String sql = "DELETE FROM Role WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() == 1;
		}
	}
}
