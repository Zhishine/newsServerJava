package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseManager {
 private static DatabaseManager instance=new DatabaseManager();
 String driver = "com.mysql.jdbc.Driver";
 String databaseUrl = "jdbc:mysql://127.0.0.1:3306/news";
 String user = "root";
 String password = "";
 public static DatabaseManager getInstance()
 {
	 return instance;
 }
 
 public Connection getConnection() throws ClassNotFoundException, SQLException
 {
	Class.forName(driver);
	//连续数据库
	Connection conn = DriverManager.getConnection(databaseUrl, user, password);
	if(conn.isClosed())
		return null;
	return conn;
 }
 
 public Statement getStatement()throws SQLException, ClassNotFoundException
 {
	 Connection conn=getConnection();
	 if(conn==null)
		 return null;
	 return conn.createStatement();
 }
}
