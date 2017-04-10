package usr.work.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBO {

	
	public static String DRIVER;
	public static String URL;
	public static String USER;
	public static String PASSWORD;

	static {
		InputStream inputStream = new DBO().getClass().getClassLoader().getResourceAsStream("db.properties");
		//创建properties
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DRIVER = properties.getProperty("DRIVER");   
		URL = properties.getProperty("URL"); 
		USER = properties.getProperty("USER"); 
		PASSWORD = properties.getProperty("PASSWORD"); 
	}

	public static Connection getConn() {
		Connection conn =null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement pstmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
		close(conn, pstmt);
	}
	
	public static void close(Connection conn, PreparedStatement pstmt){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt = null;
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
