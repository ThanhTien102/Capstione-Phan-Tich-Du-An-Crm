package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Status;

public class StatusDAO {

	public List<Status> getAll() {
		List<Status> list = new ArrayList<>();
		String sql = "SELECT * FROM Status ORDER BY id ASC";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String name = null;
				try {
					name = rs.getString("name_status");
				} catch (SQLException e) {
					name = rs.getString("name");
				}
				list.add(new Status(rs.getInt("id"), name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Status getById(int id) {
		String sql = "SELECT * FROM Status WHERE id = ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String name = null;
					try {
						name = rs.getString("name_status");
					} catch (SQLException e) {
						name = rs.getString("name");
					}
					return new Status(rs.getInt("id"), name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

