package models;

public class Role {
	private int id;
	private String nameRole ;
	private String description;
	
	// contrucor khong tham so
	public Role() {}
	
	public Role(int id, String nameRole, String description) {
		this.id = id;
		this.nameRole = nameRole;
		this.description = description;
	}
	public Role( String nameRole, String description) {
		this.nameRole = nameRole;
		this.description = description;

	}
	
	// GETTER SETTER
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
