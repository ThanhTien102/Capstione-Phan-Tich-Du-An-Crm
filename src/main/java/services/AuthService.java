package services;

import org.mindrot.jbcrypt.BCrypt;

import models.User;
import repositories.UserRepository;

public class AuthService {
	private final UserRepository userRepository;
	
	public AuthService() {
		userRepository = new UserRepository();
	}
	

	public User login(String email, String password) {
		// kiem tra rong 
		if(email == null || email.trim() == null
				|| password == null || password.trim() == null) {
			return null;
		}
		// lay user tu repository
		User user =  userRepository.findByEmail(email);
		
		// khong tim thay
		if(user == null) {
			return null;
		}
		// pass khong dung
//		if(!user.getPassword().equals(password)) {
//			return null;
//		}
		boolean checkValid = BCrypt.checkpw(password,user.getPassword());
		if(!checkValid) {
			return null;
		}
		// cus phap tao pass bam 
		// dung khi tao user
//		String hashedPassword = BCrypt.hashpw(
//			    "123456",
//			    BCrypt.gensalt(12)
//			);
		return user;
	}
}
