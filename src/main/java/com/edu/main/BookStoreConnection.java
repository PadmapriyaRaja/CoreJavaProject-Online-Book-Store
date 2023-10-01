package com.edu.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookStoreConnection {
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/the_cozy_library";
	private static final String un="root";
	private static final String pass = "root";
	private static Connection con = null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;
	
	private BookStoreConnection() {}
	
	public static Connection getConnection() {
		try {
			  Class.forName(driver);
			  if(con == null) {
					con = DriverManager.getConnection(url, un, pass);
				}
			}catch(Exception e) {
			e.printStackTrace();
		   }
		return con;
	}

}
