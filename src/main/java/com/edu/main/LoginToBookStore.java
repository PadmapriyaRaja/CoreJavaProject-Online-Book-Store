package com.edu.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginToBookStore {
	
	private static  Connection con;
	private static PreparedStatement pst;
	private static String sql;
	private static ResultSet rs;
	private static String uname ,pword,uemail,urp;;

	public static boolean check() throws SQLException {
		
	    Scanner sc = new Scanner(System.in);
		con= BookStoreConnection.getConnection();
		
		System.out.println("Enter your username or emailid:");
		urp = sc.next();
		
		final String regex = "^(.+)@(.+)$";  
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(urp);
	    if(matcher.matches()) {
	    	uemail = urp;
	    	System.out.println("Enter the password:");
			pword = sc.next();
			sql = "select * from login where email = ?  AND  password = ?";
			pst=con.prepareStatement(sql);
			pst.setString(1, uemail);
			pst.setString(2, pword);
			rs = pst.executeQuery();
			while(rs.next()) {
				uname = rs.getString("username");
				if(pword.equals(rs.getString("password"))){	
				   return true;
				}else {
					System.err.println("Password is incorrect");
					return false;
				}
			}
			return false;
			
	    }else {
	    	uname = urp;
	    	System.out.println("Enter the password:");
			pword = sc.next();
			sql = "select * from login where username=?  AND  password = ?";
			pst=con.prepareStatement(sql);
			pst.setString(1, uname);
			pst.setString(2, pword);
			rs = pst.executeQuery();
			while(rs.next()) {
				uemail = rs.getString("email");
				if(pword.equals(rs.getString("password"))){
				   return true;
				}else {
					System.err.println("Password is incorrect");
					return false;
				}
			}
			return false;
			
		
	    }

	}

	public static String getUname() {
		return uname;
	}

	
	public static String getUemail() {
		return uemail;
	}

	
	
	

}
