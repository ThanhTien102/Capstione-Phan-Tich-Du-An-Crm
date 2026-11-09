package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	// =====================
	// GET ALL
	// =====================
	public List<User> getAllUser(){
		List<User> users = new ArrayList<User>();
		
		String query = "SELECT * FROM User Order By id DESC";
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pre = conn.prepareStatement(query)){
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
	            user.setPhone(rs.getString("phone"));
	            user.setCountry(rs.getString("country"));
	            user.setRoleId(rs.getInt("role_id"));
	            // them vao list
	            users.add(user);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return users;
	}
	// =====================
	// Create
	// =====================
	public boolean create(User user) {
		String query = "INSERT INTO User (email, password, fullname, phone, country, role_id)"
				+ " Values (?, ?, ?, ?, ?, ?)";
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pre = conn.prepareStatement(query)) {
			
			pre.setString(1, user.getEmail());
			pre.setString(2, user.getPassword());
			pre.setString(3, user.getFullname());
			pre.setString(4, user.getPhone());
			pre.setString(5, user.getCountry());
			pre.setInt(6, user.getRoleId());
			
			return pre.executeUpdate() == 1;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
}









