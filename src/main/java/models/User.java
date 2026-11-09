package models;

public class User {
	private int id;
	private String fullname;
	private String email;
	private String phone;
	private String country;
	private String password;
	private int roleId;

	public User() {
	}

	public User(int id, String email, 
			String password, String fullname, 
			String phone, String country, int roleId) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.country = country;
		this.roleId = roleId;
	}
	
	public User(String email, 
			String password, String fullname, 
			String phone, String country, int roleId) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.country = country;
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	

}
