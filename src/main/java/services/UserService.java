package services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dto.CreateUserDTO;
import models.User;
import repositories.UserRepository;

public class UserService {

	private final UserRepository userRepository;
	
	public UserService() {
		userRepository = new UserRepository();
	}
	
	//GETALL
	public List<User> getAllUser(){
		List<User> users = userRepository.getAllUser();
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
	
	
}
