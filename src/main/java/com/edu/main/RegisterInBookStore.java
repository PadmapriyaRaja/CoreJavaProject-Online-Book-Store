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
	private static boolean isValid;
	
	
	public static void signup() throws SQLException {
		
		 sc = new Scanner(System.in);
		
			String uname,pword="",pword1="",uemail;
			
			while(true) {
				System.out.println("Enter the usernmae:");
				uname=sc.nextLine();
				isValid = Validation.isValidUsername(uname);
				if(isValid) {
					break;
				}else {
					System.out.println("User name not valid" +"A username is considered valid if all the following constraints are satisfied:\r\n"
							+ "*The username should contain of 5 to 30 characters inclusive.\r\n"
							+ "*The username can only contain alphanumeric characters and underscores (_).\r\n"
							+ "*The first character of the username must be an alphabetic character, i.e., either lowercase character [a – z] or uppercase character [A – Z].");
				}
			}
			
			while(true) {
				System.out.println("Enter Email ID : ");
				uemail=sc.nextLine();
				isValid = Validation.isValidUseremail(uemail);
				if(isValid) {
					break;
				}else {
					System.out.println("Email not valid" );
				}
			}
		    while(true) {
		    	while(true) {
		    	System.out.println("Enter the Password : ");
		    	pword=sc.nextLine();
		    	isValid = Validation.isValidpassword(pword);
		    	if(isValid) {
		    		break;
		    	}else {
		    		System.out.println("Password is not valid" );
		    		System.out.println("A password is considered valid if all the following constraints are satisfied:\r\n"
			    			+ "*It should contain at least 8 characters and at most 20 characters.\r\n"
			    			+ "*It should contain at least one digit.\r\n"
			    			+ "*It should contain at least one upper case alphabet.\r\n"
			    			+ "*It should contain at least one lower case alphabet.\r\n"
			    			+ "*It should contain at least one special character. \r\n"
			    			+ "*It should not contain any white space.");
		    	}
		    	}
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
