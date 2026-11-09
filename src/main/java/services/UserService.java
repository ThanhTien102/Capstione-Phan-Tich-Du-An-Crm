package services;

import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dto.CreateUserDTO;
import dto.UserListDTO;
import models.User;
import repositories.UserRepository;

public class UserService {

	private final UserRepository userRepository;
	
	public UserService() {
		userRepository = new UserRepository();
	}
	
	//GETALL
	public List<UserListDTO> getAllUser(){
		List<UserListDTO> users = userRepository.getAllUser();
		return users;
	}
	
	// create 
	public String create(CreateUserDTO dto) {
		// bắt lỗi
		if(dto.getEmail() == null || dto.getEmail().trim().isEmpty()
			|| dto.getPassword() == null || dto.getPassword().trim().isEmpty()
			|| dto.getFullname() == null || dto.getFullname().trim().isEmpty()
			|| dto.getPhone() == null || dto.getPhone().trim().isEmpty()
				) {
			return "Email, mật khẩu, sdt và họ tên không được để trống";
		}
		// check trung email
		User findByEmail = userRepository.findByEmail(dto.getEmail());
		if(findByEmail != null) {
			return "Email đã tồn tại.";

		}
		// tiến hành thêm dự liệu 
		// mã hoá mật khẩu 
		String hashPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12));
		// convert nội dung CreateuserDto => User 
		User user = new User(dto.getEmail().trim(),
				hashPassword,dto.getFullname(), dto.getPhone(), dto.getCountry(),dto.getRoleId());
		// goij repo creater(User )
		return userRepository.create(user) ? null : "Khong the tao user";
	}
	
	// userPaging
	public List<UserListDTO> getUserPaging(String keywords, int size, int index){
		List<UserListDTO> users = userRepository.userPaging(keywords, size, index);
		return users;
	} 
	// countUser
	public int countUsers(String keyword) throws SQLException {
	    return userRepository.countUser(keyword);
	}
	// delete
	public String deleteById(int id) 
			throws SQLException{
		try {
			boolean delete = userRepository.deleteById(id);
			// null => thanfh cong
			return delete ?
					null : 
				"Khong tim thay user";
		} catch (SQLException e) {
			// TOD: handle exception
			return "User dang co task khong duoc xoa";
		}
	}
	// get user by id
	public User findById(int id) throws SQLException {
		return userRepository.getUserById(id);
	}
	
	// update
	public String update(User user) throws SQLException {
		// kiem tra rong
		if(user.getFullname().trim().isEmpty() || user.getFullname()== null ||
			user.getPhone().trim().isEmpty() || user.getPhone()== null ||
			user.getEmail().trim().isEmpty() || user.getEmail()== null)
		{
			return "Email, ho ten hoac so dien thoai khong duoc de trong";
		}
		// kiem tra trung email
		User checkEmail = userRepository.findByEmail(user.getEmail());
		if(checkEmail != null) { // da ton tai email
			return "Email da duoc su dung";
		}
		try {
			boolean check = userRepository.updateUser(user);
			return check ? null: "Loi update";
		} catch (Exception e) {
			// TODO: handle exception
			return "Loi update";
		}
	}
	
	
	
	
	
}
