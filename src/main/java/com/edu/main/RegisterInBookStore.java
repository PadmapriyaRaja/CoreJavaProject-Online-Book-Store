package com.edu.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterInBookStore {
	
	private static  Connection con;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	private static String s;
	private static Scanner sc;
	
	public static void signup() throws SQLException {
		
		 sc = new Scanner(System.in);
		
			String uname,pword="",pword1="",uemail;
			
			System.out.println("Enter the user Name : ");
		    uname=sc.nextLine();
		    System.out.println("Enter  Email ID : ");
		    uemail=sc.nextLine();
		    
		    while(true) {
		    	System.out.println("Enter the Password : ");
		    	pword=sc.nextLine();
		    	System.out.println("Re-enter the password :");
		    	pword1=sc.nextLine();
		    	if(pword.equals(pword1)) {
		    		break;
		    	}else {
		    		System.out.println("----Password mismatched!!! Please Enter Correctly----");
		    	}
		    }
		    
		    con = BookStoreConnection.getConnection();
		    sc = new Scanner(System.in);
		    s = "select * from login where email = ?";
		    pst = con.prepareStatement(s);
		    pst.setString(1, uemail);	
		    rs = pst.executeQuery();
		    if(!rs.next()) {
		    
		    	sql = "insert into login values(?,?,?,?)";
		    	pst = con.prepareStatement(sql);
		    	pst.setString(1, uname);
		    	pst.setString(2,pword);
		    	pst.setString(3,uemail);
		    	pst.setInt(4, 1);
		    
		    	int i = pst.executeUpdate();
		    	if(i>0) {
		    		System.out.println("Registered Successfully..");
		    	}else {
		    		System.out.println("Error Occured..");
		    	}
		    	
		    }else {
		    	System.out.println("Already user exist..");
		    }
		    
	}
	
}
