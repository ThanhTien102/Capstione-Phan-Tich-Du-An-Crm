package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final static String url = "jdbc:mysql://localhost:3307/CRM_DB"
			+ "?useUnicode=true"
			+ "&characterEncoding=UTF-8"
			+ "&useSSL=false"
			+ "&allowPublicKeyRetrieval=true";
	private final static String username = "root";
	private final static String password = "admin123";
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, username, password);
		} catch(Exception e) {
			try {
				return DriverManager.getConnection(url, username, "Root@123456");
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return null;
	}
}
