package models;

public class Status {
	private int id;
	private String nameStatus;
	
	public Status() {}
	
	public Status(int id, String nameStatus) {
		this.id = id;
		this.nameStatus = nameStatus;
	}
	public Status(String nameStatus) {
		this.nameStatus = nameStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameStatus() {
		return nameStatus;
	}

	public void setNameStatus(String nameStatus) {
		this.nameStatus = nameStatus;
	}
	
	
}
