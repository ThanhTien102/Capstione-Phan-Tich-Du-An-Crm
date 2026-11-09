package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final static String url = "jdbc:mysql://localhost:3307/CRM_DB";
	private final static String username = "root";
	private final static String password = "Root@123456";
	
	public static Connection getConnection() {
		try {
			// van chay code binh thuong trong try
			// neu try loi thif nhay xuong catch
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, username,password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
