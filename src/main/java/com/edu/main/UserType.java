package com.edu.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class UserType {
	
	private static  Connection con;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	private static Scanner sc;
	
	public static int user() throws SQLException {
		
		int userType = 0;
		con = BookStoreConnection.getConnection();
	    sc = new Scanner(System.in);
	    sql = "select * from login where username=? or email=?";
	    pst = con.prepareStatement(sql);
	    pst.setString(1,LoginToBookStore.getUname());
		pst.setString(2, LoginToBookStore.getUemail() );
		rs=pst.executeQuery();
		while(rs.next()) {
			userType = rs.getInt("usertype");
		}
		
		return userType;
	}

}
