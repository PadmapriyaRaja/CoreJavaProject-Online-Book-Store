package com.edu.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Validation {

private static String regex;


	public static boolean isValidUsername(String username) {
		
        regex = "^[A-Za-z]\\w{5,29}$"; 
        Pattern p = Pattern.compile(regex); 
        if (username == null) { 
            return false; 
        } 
        Matcher m = p.matcher(username); 
        return m.matches(); 
        
	}

	public static boolean isValidUseremail(String useremail) {
		regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
		 Pattern pat = Pattern.compile(regex);
	        if (useremail == null)
	            return false;
	        return pat.matcher(useremail).matches();
		
	}
	
	public static boolean isValidpassword(String password) {
		regex ="^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		 Pattern p = Pattern.compile(regex);
		 
	        if (password == null) {
	            return false;
	        }
	        Matcher m = p.matcher(password);
	        return m.matches();
	}

	
	
}
