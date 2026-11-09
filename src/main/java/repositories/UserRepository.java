package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.DBConnection;
import models.User;

public class UserRepository {
	// findByEmail
	public User findByEmail(String email) {
		// vieet cau query
		String query = "SELECT * FROM User WHERE email = ? LIMIT 1";
		try (Connection conn = DBConnection.getConnection();
			// chuan bi cau query - tham so
			PreparedStatement pre = conn.prepareStatement(query)) 
		{
			pre.setString(1, email);
			ResultSet resultSet = pre.executeQuery();
			if(resultSet.next()) {
				return new User(
			            resultSet.getInt("id"),
			            resultSet.getString("email"),
			            resultSet.getString("password"),
			            resultSet.getString("fullname"),
			            resultSet.getString("phone"),
			            resultSet.getString("country"),
			            resultSet.getInt("role_id")
						);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
}
