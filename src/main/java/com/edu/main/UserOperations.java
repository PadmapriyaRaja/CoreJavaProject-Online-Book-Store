package com.edu.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class UserOperations {

	private static Connection con;
	private static ResultSet rs;
	private static PreparedStatement pst;
	private static int bookid, choiceview, reqquantity, availquantity;
	private static Scanner sc;
	private static char c;
	private static float bookprice,total_price,grand_total=0;
	private static String s, sql, bookname, author, category,address,phonenumber,pincode,orderDate;
	
	
	
	public static void viewBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		System.out.println("Choose the type by which you want to view books");

		System.out.println("1)All books\n2)By Category\n3)By Book\n4)By Author");
		System.out.println("Enter your choice:");
		choiceview = sc.nextInt();
		if (choiceview == 1) {
			s = "select * from book";
			pst = con.prepareStatement(s);
		} else if (choiceview == 2) {
			System.out.println("Enter the Category name : ");
			sc.nextLine();
			category = sc.nextLine();
			s = "select * from book where category = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, category);
		} else if (choiceview == 3) {
			System.out.println("Enter the Book name : ");
			sc.nextLine();
			bookname = sc.nextLine();
			s = "select * from book where bookname = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, bookname);
		} else if (choiceview == 4) {
			System.out.println("Enter the Author name : ");
			sc.nextLine();
			author = sc.nextLine();
			s = "select * from book where authorname = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, author);
		} else {
			System.out.println("***INVALID CHOICE !!! PLEASE TRY AGAIN***");
		}
		rs = pst.executeQuery();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %22s %19s %18s  %18s", "BookId", "BookName", "Author", "Price", "Category",
				"Quantity");
		System.out.println();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		while (rs.next()) {
			System.out.format("%5d %25s %25s %12f %18s %18d", rs.getInt("bookid"), rs.getString("bookname"),
					rs.getString("authorname"), rs.getFloat("bookprice"), rs.getString("category"),
					rs.getInt("quantity"));
			System.out.println();
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

	}

	public static void addToCart() throws SQLException {//total 

		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		System.out.println("Enter the book name that to be added to cart:");
		bookname = sc.nextLine();
		System.out.println("Enter the book id:");
		bookid = sc.nextInt();
		s = "select * from book where bookid = ?";
		pst = con.prepareStatement(s);
		pst.setInt(1, bookid);
		rs = pst.executeQuery();
		if (rs.next()) {
			System.out.println("Enter the quantity you want to purchase:");
			reqquantity = sc.nextInt();
			availquantity = rs.getInt("quantity");
			bookprice = rs.getFloat("bookprice");
			total_price = reqquantity * bookprice;
			if (reqquantity <= availquantity) {
				s = "insert into add_to_cart (bookid,bookname,authorname,bookprice,category,quantity) select bookid,bookname,authorname,bookprice,category,quantity from book where bookid = ?";
				pst = con.prepareStatement(s);
				pst.setInt(1, bookid);
				int i = pst.executeUpdate();
				if (i > 0) {
					sql = "update add_to_cart set quantity = ?,total_price =?  where bookid = ?";
					pst = con.prepareStatement(sql);
					pst.setInt(1, reqquantity);
					pst.setFloat(2, total_price);
					pst.setInt(3,bookid);
					int j = pst.executeUpdate();
					if (j > 0) {
						System.out.println("Successfully added to cart");
					} else {
						System.out.println("Error Occured while adding");
					}

				} else {
					System.out.println("Error Occured");
				}
			} else {
				System.out.println("Out of stock");
			}
		} else {
			System.out.println("Book not Found");
		}
//		System.out.println("Do you want to add items to cart?");
//		System.out.println("YES - Y");
//		System.out.println("Enter your choice:");
//		c = sc.next().toUpperCase().charAt(0);
//		if(c == 'Y') {
//			UserOperations.viewBooks();
//			UserOperations.addToCart();
//		}

	}

	public static void buyBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		s = "select * from add_to_cart";
		pst = con.prepareStatement(s);
		rs = pst.executeQuery();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %22s %19s %18s  %18s %18s", "BookId", "BookName", "Author", "Price", "Category",
				"Quantity","Total_Price");
		System.out.println();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		while (rs.next()) {
			grand_total += rs.getFloat("total_price");
			System.out.format("%5d %25s %25s %12f %18s %18s %18f", rs.getInt("bookid"), rs.getString("bookname"),
					rs.getString("authorname"), rs.getFloat("bookprice"), rs.getString("category"),
					rs.getInt("quantity"),rs.getFloat("total_price"));
			System.out.println();
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Place Order");
        System.out.println("1)Yes - Y");
        System.out.println("2)No  - N");
        System.out.println("Enter your choice:");
        c = sc.next().toUpperCase().charAt(0);
        if(c == 'Y') {
        	System.out.println("Enter your address:");
        	sc.nextLine();
        	address = sc.nextLine();
        	System.out.println("Enter your pincode:");
        	pincode = sc.next();
        	System.out.println("Enter your phone number:");
        	phonenumber = sc.next();

        	LocalDateTime currentDate = LocalDateTime.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	orderDate = currentDate.format(formatter);
        	
        	
        	s = "insert into order_table (username,email,address,pincode,phonenumber,order_date,bookid,bookname,authorname,bookprice,category,quantity,total_price)"
        			+"select ?,?,?,?,?,?,bookid,bookname,authorname,bookprice,category,quantity,total_price"
        			+ " from add_to_cart where bookid = ?";

        	pst = con.prepareStatement(s);
        	pst.setString(1,LoginToBookStore.getUname());
        	pst.setString(2,LoginToBookStore.getUemail());
        	pst.setString(3, address);
        	pst.setString(4, pincode);
        	pst.setString(5, phonenumber);
        	pst.setString(6, orderDate);
        	pst.setInt(7, bookid);
        	int i = pst.executeUpdate();
        	
        	if(i>0) {
        		System.out.println("Pay Rs."+grand_total);
        		System.out.println("Payment in progress...");
        		System.out.println("Payment successfull");
        		System.out.println("Order placed Successfully");
        		s = "delete from add_to_cart where bookid=?";
        		pst = con.prepareStatement(s);
        		pst.setInt(1, bookid);
        		int j = pst.executeUpdate();
        		if(j>0) {
        			System.out.println("Thanks for your shopping!");
        			System.out.println("Do you want to purchase again?");
        			System.out.println("YES - Y");
        			System.out.println("Enter your choice:");
        			c = sc.next().toUpperCase().charAt(0);
        			if(c == 'Y') {
        				UserOperations.viewBooks();
        				UserOperations.addToCart();
        			}
        		}else {
        			System.out.println("Some error occured!");
        		}
        		
        	}else {
        		System.out.println("Errror Occured!");
        	}
        	
        }

	}

	public static void getPaymentReceipt() {
		
		System.out.println("Name:"+LoginToBookStore.getUname());
		System.out.println("Address:"+address);
		System.out.println("Date"+orderDate);
		System.out.println("Total amount paid:"+grand_total);
	}

}
