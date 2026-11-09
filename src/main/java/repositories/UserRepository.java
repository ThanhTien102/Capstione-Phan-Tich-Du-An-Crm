package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import dto.UserListDTO;
import models.User;

public class UserRepository {
	// findByEmail
	public User findByEmail(String email) {
		// vieet cau query
		String query = "SELECT * FROM User WHERE email = ? LIMIT 1";
		try (Connection conn = DBConnection.getConnection();
				// chuan bi cau query - tham so
				PreparedStatement pre = conn.prepareStatement(query)) {
			pre.setString(1, email);
			ResultSet resultSet = pre.executeQuery();
			if (resultSet.next()) {
				return new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("fullname"), resultSet.getString("phone"), resultSet.getString("country"),
						resultSet.getInt("role_id"));
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
	public List<UserListDTO> getAllUser() {
		List<UserListDTO> users = new ArrayList<UserListDTO>();

		String query = "SELECT u.id, email, fullname, phone, name_role   FROM User u "
				+ "inner join Role r on u.role_id = r.id" + " Order By u.id DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pre = conn.prepareStatement(query)) {
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				UserListDTO user = new UserListDTO(
						rs.getInt("id"), 
						rs.getString("fullname"), 
						rs.getString("email"),
						rs.getString("phone"), 
						rs.getString("name_role"));
				// them vao list
				users.add(user);
			}

		} catch (Exception e) {
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
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pre = conn.prepareStatement(query)) {

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
// userPaging
	public List<UserListDTO> userPaging(String keywords, int size, int index ){
		List<UserListDTO> users = new ArrayList<>(); 
		// sql
		String sql = "select u.id, fullname, email, u.phone, r.name_role   from `User` u "
				+ "inner join `Role` r  "
				+ "on u.role_id  = r.id "
				+ "where u.fullname  like ? " // '%{keyword}%'
				+ "or u.email  like ? "
				+ "Order By u.id DESC "
				+ "limit ? offset ? ";
			
		try(Connection conn = DBConnection.getConnection()) {
			if(conn == null) {// loi neu khong ket noi db duoc
				throw new SQLException("Khong ket noi duoc co so du lieu");
			}
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				String searchValue = "%" + keywords + "%";
				ps.setString(1, searchValue);	
				ps.setString(2, searchValue);
				ps.setInt(3, size);
				ps.setInt(4, size * (index - 1));
				
				try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						users.add(new UserListDTO(
							rs.getInt("id"), 
							rs.getString("fullname"), 
							rs.getString("email"),
							rs.getString("phone"), 
							rs.getString("name_role")
						));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
	// countuser
	public int countUser(String keywords) throws SQLException {
		String sql = "select count(*)   from `User` u "
				+ "inner join `Role` r  "
				+ "on u.role_id  = r.id "
				+ "where u.fullname  like ?"
				+ "or u.email  like ?";
		try(Connection conn = DBConnection.getConnection()){
			if(conn == null) {// loi neu khong ket noi db duoc
				throw new SQLException("Khong ket noi duoc co so du lieu");
			}
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				String searchValue = "%" + keywords + "%";
				ps.setString(1, searchValue);	
				ps.setString(2, searchValue);
				
				try(ResultSet rs = ps.executeQuery()){
					rs.next();
					return rs.getInt(1);
				}
			}
		}
	}
	// DeleteById
	public boolean deleteById(int id) throws SQLException {
		String sql = "DELETE FROM User WHERE id = ?";
		try(Connection conn = DBConnection.getConnection()) {
			if(conn == null) {// loi neu khong ket noi db duoc
				throw new SQLException("Khong ket noi duoc co so du lieu");
			}
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				ps.setInt(1, id);
				return ps.executeUpdate() == 1; // co 1 dong thay doi trong db 
			}
			
		}
		
	}
	
	
}
