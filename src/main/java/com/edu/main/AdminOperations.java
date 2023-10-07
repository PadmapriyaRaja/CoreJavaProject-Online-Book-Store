package com.edu.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class AdminOperations {
	
	private static Scanner sc;
	private static String s,sql,bookname,author,category,startDate,endDate;
	private static  Connection con;
	private static ResultSet rs;
	private static PreparedStatement pst;
	private static int bookid,quantity;
	private static float price,availprice,updateprice;
	

	public static void viewBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		s = "select * from book";
		 pst=con.prepareStatement(s);
		 rs = pst.executeQuery();
		 System.out.println("------------------------------------------------------------------------------------------------------------------");  
		 System.out.format("%5s %20s %22s %19s %18s %18s", "BookId", "BookName", "Author", "Price" , "Category","Quantity");  
		 System.out.println();  
		 System.out.println("------------------------------------------------------------------------------------------------------------------");  
		 while(rs.next()) {
			 System.out.format("%5d %25s %25s %12f %18s %18d", rs.getInt("bookid"), rs.getString("bookname"), rs.getString("authorname"), rs.getFloat("bookprice") , rs.getString("category"),rs.getInt("quantity"));
			 System.out.println();  	 
		 }
		 System.out.println("------------------------------------------------------------------------------------------------------------------");  
		 
		
		
	}


	public static void insertBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		while(true) {
		System.out.println("Enter the book id to be added:");
		bookid = sc.nextInt();
		if(bookid>0) {
			break;
		}else {
			System.out.println("*Invalid bookid.Please enter the valid bookid");
		}
		}
		s = "select * from book where bookid = ?";
		pst = con.prepareStatement(s);
		pst.setInt(1, bookid);
		rs = pst.executeQuery();
		if(!rs.next()) {
			 System.out.println("Enter Book name:");
			 sc.nextLine();
			 bookname = sc.nextLine();
			 System.out.println("Enter Author name:");
			 author = sc.nextLine();
			 while(true) {
			 System.out.println("Enter the book price:");
			 price = sc.nextFloat();
			 if(price>0) {
				 break;
			 }else {
				 System.out.println("*Invalid price.Please enter again");
			 }
			 }
			 System.out.println("Enter the category:");
			 sc.nextLine();
			 category = sc.nextLine();
			 while(true) {
			 System.out.println("Enter the quantity of book:");
			 quantity = sc.nextInt();
			 if(quantity>0) {
				 break;
			 }else {
				 System.out.println("*Invalid quantity.Please enter again");
			 }
			 }
			
			 sql = "insert into book values (?,?,?,?,?,?)";
			 
			 pst = con.prepareStatement(sql);
			 pst.setInt(1, bookid);
			 pst.setString(2, bookname);
			 pst.setString(3, author);
			 pst.setFloat(4, price);
			 pst.setString(5, category);
			 pst.setInt(6, quantity);
			 
			 int i = pst.executeUpdate();
			 if(i>0) {
				 System.out.println("Added the book successfully");
			 }else {
				 System.out.println("Some error occured..");
			 }
		}else {
			System.out.println("----Book already exist----Please enter new book");
		}
		
	}


	public static void deleteBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter the book id to be deleted:");
			bookid = sc.nextInt();
			if(bookid>0) {
				break;
			}else {
				System.out.println("*Invalid bookid.Please enter the valid bookid");
			}
			}
	    s = "select * from book where bookid = ?";
	    pst = con.prepareStatement(s);
	    pst.setInt(1, bookid);
	    rs = pst.executeQuery(); 
	    if(rs.next()) {
	    	 s = "delete from book where bookid = ?";
	    	 pst = con.prepareStatement(s);
	    	 pst.setInt(1, bookid);
	    	 int i = pst.executeUpdate();
	    	 if(i>0) {
	    		 System.out.println("Deleted record successfully");
	    	 }else {
	    		 System.out.println("outor occured");
	    	 }
	    }else {
	    	System.out.println("----Book not found----");
	    }
	    
		
	}


	public static void update() throws SQLException  {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter the book id to be updated:");
			bookid = sc.nextInt();
			if(bookid>0) {
				break;
			}else {
				System.out.println("*Invalid bookid.Please enter the valid bookid");
			}
		}
		s = "select * from book where bookid = ?";
		pst = con.prepareStatement(s);
		pst.setInt(1, bookid);
		rs = pst.executeQuery();
		if(rs.next()) {
			System.out.println("1)UPDATE BOOKPRICE\n2)UPDATE QUANTITY");
			int choice = sc.nextInt();
			
			
				
			if(choice == 1){
				availprice = rs.getFloat("bookprice");
				if(price<=availprice) {
				while(true) {
					System.out.println("Enter the price of the book to be updated:");
					price = sc.nextFloat();
					if(price<0) {
						System.out.println("Please enter the valid price");
					}
					else {
						break;
					}
					}
				System.out.println("1)INCREASE BOOKPRICE\n2)DECREASE BOOKPRICE");
				int pricechoice = sc.nextInt();
				if(pricechoice == 1) {
					updateprice = availprice + price;
				}else if(pricechoice == 2) {
					updateprice = availprice - price;
				}
				s = " update book set bookprice = ? where bookid = ?  ";
				pst = con.prepareStatement(s);
				pst.setFloat(1,updateprice);
				pst.setInt(2, bookid);
				int i = pst.executeUpdate();
				if(i>0) {
					System.out.println("Updated the bookprice successfully");
				}else {
					System.out.println("outor occured");
				}
			}else {
				System.out.println("Invalid amount");
			}
			
			}else if(choice == 2) {
					while(true) {
					System.out.println("Enter the quantity of the book to be updated:");
					quantity = sc.nextInt();
					if(quantity<0) {
						System.out.println("Please enter the valid quantity");
					}else {
						break;
					}
					}
					s = " update book set quantity = ? where bookid = ?  ";
					pst = con.prepareStatement(s);
					pst.setFloat(1,quantity);
					pst.setInt(2, bookid);
					int i = pst.executeUpdate();
					if(i>0) {
						System.out.println("Updated the bookquantity successfully");
					}else {
						System.out.println("Error occured");
					}
			}else {
				System.out.println("***Invalid Choice*** Please try again!!!");
			}
		}else {
			System.out.println("----Book not found----");
		}
	}
	
	public static void showHistory() throws SQLException {
		
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		System.out.println("Enter the start date:YYYY-MM-DD");
		 startDate = sc.next();
		System.out.println("Enter the end date:YYYY-MM-DD");
		 endDate = sc.next();
		 startDate = startDate+"00:00:00";
		 endDate = endDate+"24:00:00";
		s = "select * from order_table where order_date>=? and order_date<=?";
		pst = con.prepareStatement(s);
		pst.setString(1, startDate);
		pst.setString(2, endDate);
		rs = pst.executeQuery();
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%-10s %-15s %-30s %-40s %-10s %-15s %-10s %-30s %-20s %-10s %-15s %-10s %-15s %-20s%n",
                "OrderID", "Username", "Email", "Address", "Pincode", "Phone Number", "BookID", "Book Name",
                "Author Name", "Book Price", "Category", "Quantity", "Total Price", "Order Date");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	
		while (rs.next()) {
			
			System.out.format("%-10d %-15s %-30s %-40s %-10s %-15s %-10d %-30s %-20s %-10.2f %-15s %-10d %-15.2f %-20s%n",rs.getInt("orderid"), rs.getString("username"),rs.getString("email"),
					rs.getString("address"),rs.getString("pincode"),rs.getString("phonenumber"),rs.getInt("bookid"),rs.getString("bookname"),rs.getString("authorname"), rs.getFloat("bookprice"),
					rs.getString("category"),rs.getInt("quantity"),rs.getFloat("total_price"),rs.getString("order_date"));
			System.out.println();
		}
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		
				
		
	}

}
