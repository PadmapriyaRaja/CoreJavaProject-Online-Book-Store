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
	private static String RESET = "\u001B[0m";
	private static String RED = "\u001B[31m";
	
	public static void signup() throws SQLException {
		
		 sc = new Scanner(System.in);
		
			String uname,pword="",pword1="",uemail;
			
			while(true) {
				System.out.println("Enter the user Name : ");
				System.out.println("A username is considered valid if all the following constraints are satisfied:\r\n"
						+ "\r\n"
						+ "The username consists of 6 to 30 characters inclusive. If the username\r\n"
						+ "consists of less than 6 or greater than 30 characters, then it is an invalid username.\r\n"
						+ "The username can only contain alphanumeric characters and underscores (_). Alphanumeric characters describe the character set consisting of lowercase characters [a – z], uppercase characters [A – Z], and digits [0 – 9].\r\n"
						+ "The first character of the username must be an alphabetic character, i.e., either lowercase character\r\n"
						+ "[a – z] or uppercase character [A – Z].");
				uname=sc.nextLine();
				isValid = Validation.isValidUsername(uname);
				if(isValid) {
					break;
				}else {
					System.out.println(RED +"User name not valid" + RESET);
				}
			}
			
			while(true) {
				System.out.println("Enter Email ID : ");
				uemail=sc.nextLine();
				isValid = Validation.isValidUseremail(uemail);
				if(isValid) {
					break;
				}else {
					System.out.println(RED +"Email not valid" + RESET);
				}
			}
		    while(true) {
		    	while(true) {
		    	System.out.println("Enter the Password : ");
		    	System.out.println("A password is considered valid if all the following constraints are satisfied:\r\n"
		    			+ "\r\n"
		    			+ "It contains at least 8 characters and at most 20 characters.\r\n"
		    			+ "It contains at least one digit.\r\n"
		    			+ "It contains at least one upper case alphabet.\r\n"
		    			+ "It contains at least one lower case alphabet.\r\n"
		    			+ "It contains at least one special character which includes !@#$%&*()-+=^.\r\n"
		    			+ "It doesn’t contain any white space.");
		    	pword=sc.nextLine();
		    	isValid = Validation.isValidpassword(pword);
		    	if(isValid) {
		    		break;
		    	}else {
		    		System.out.println(RED +"Password is not valid" + RESET);
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
