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
	private static int count,order_id=0, bookid, choiceview, choiceUpdate,quantity,reqquantity, availquantity;
	private static Scanner sc;
	private static char c;
	private static float remove_price,bookprice,total_price;
	static float grand_total=0;
	private static String s, sql, bookname, author, category,address,phonenumber,pincode,orderDate;
	private static boolean isValid;
	
	
	
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
			rs = pst.executeQuery();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------");
			System.out.format("%5s"
					+ " %20s %22s %19s %18s  %18s", "BookId", "BookName", "Author", "Price", "Category",
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

		} else if (choiceview == 2) {
			System.out.println("Enter the Category name : ");
			sc.nextLine();
			category = sc.nextLine();
			s = "select * from book where category = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, category);
			rs = pst.executeQuery();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------");
			System.out.format("%5s"
					+ " %20s %22s %19s %18s  %18s", "BookId", "BookName", "Author", "Price", "Category",
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

		} else if (choiceview == 3) {
			System.out.println("Enter the Book name : ");
			sc.nextLine();
			bookname = sc.nextLine();
			s = "select * from book where bookname = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, bookname);
			rs = pst.executeQuery();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------");
			System.out.format("%5s"
					+ " %20s %22s %19s %18s  %18s", "BookId", "BookName", "Author", "Price", "Category",
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

		} else if (choiceview == 4) {
			System.out.println("Enter the Author name : ");
			sc.nextLine();
			author = sc.nextLine();
			s = "select * from book where authorname = ? order by bookprice";
			pst = con.prepareStatement(s);
			pst.setString(1, author);
			rs = pst.executeQuery();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------");
			System.out.format("%5s"
					+ " %20s %22s %19s %18s  %18s", "BookId", "BookName", "Author", "Price", "Category",
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

		} else {
			System.out.println("***INVALID CHOICE !!! PLEASE TRY AGAIN***");
			
		}
		
	}

	public static void addToCart() throws SQLException { 

		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		while(true) {
		System.out.println("Enter the book id:");
		bookid = sc.nextInt();
		if(bookid>0) {
			break;
		}else {
			System.out.println("*Invalid bookid.Please enter again");
		}
		}
		s = "select * from book where bookid = ?";
		pst = con.prepareStatement(s);
		pst.setInt(1, bookid);
		rs = pst.executeQuery();
		if (rs.next()) {
			while(true) {
			System.out.println("Enter the quantity you want to purchase:");
			reqquantity = sc.nextInt();
			if(reqquantity>0) {
				break;
			}else {
				System.out.println("*Invalid quantity.Please enter again");
			}
			}
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

}
	
	public static void updateCart() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		s = "select * from add_to_cart";
		pst = con.prepareStatement(s);
		rs = pst.executeQuery();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %22s %19s %18s  %18s %18s", "BookId", "BookName", "Author", "Price", "Category",
				"Quantity","Total_Price");
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		
		while (rs.next()) {
			System.out.format("%5d %25s %25s %12f %18s %18s %18f", rs.getInt("bookid"), rs.getString("bookname"),
					rs.getString("authorname"), rs.getFloat("bookprice"), rs.getString("category"),
					rs.getInt("quantity"),rs.getFloat("total_price"));
			System.out.println();
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------");
		while(true) {
		System.out.println("Enter the book id from cart to be updated or removed:");
		bookid = sc.nextInt();
		if(bookid>0) {
			break;
		}else {
			System.out.println("*Invalid bookid.Please enter again");
		}
		}
		s = "select * from add_to_cart where bookid = ?";
		pst = con.prepareStatement(s);
		pst.setInt(1, bookid);
		rs = pst.executeQuery();
		if(rs.next()) {
			bookprice = rs.getFloat("bookprice");
			System.out.println("1)UPDATE QUANTITY\n2)DELETE ITEM");
			System.out.println("Enter your choice:");
			choiceUpdate = sc.nextInt();
			if(choiceUpdate == 1) {
				System.out.println("Enter the quantity to be changed:");
				reqquantity = sc.nextInt();
				if(reqquantity <= availquantity) {
					total_price = reqquantity * bookprice;
			    sql = "update add_to_cart set quantity = ?,total_price =? where bookid = ?";
			    pst = con.prepareStatement(sql);
			    pst.setInt(1, reqquantity);
			    pst.setFloat(2, total_price);
			    pst.setInt(3, bookid);
			     int i = pst.executeUpdate();
			     if(i>0) {
			    	 System.out.println("Updated the cart succesfully");
			     }else {
			    	 System.out.println("Some error occured");
			     }
				}
			}else if(choiceUpdate == 2) {
				s = "delete from add_to_cart where bookid = ?";
				pst = con.prepareStatement(s);
				pst.setInt(1, bookid);
				int j = pst.executeUpdate();
				if(j >0) {
					remove_price = rs.getFloat("total_price");
					grand_total -= remove_price;
					System.out.println("Item deleted successfully");
				}else {
					System.out.println("Some error occures");
				}
		  }else {
			  System.out.println("Invalid choice.Please enter again");
		  }
		}else {
			System.out.println("---Book not found---");
		}
		
		
		
	}

	public static void buyBooks() throws SQLException {
		con = BookStoreConnection.getConnection();
		sc = new Scanner(System.in);
		s = "select count(*) from add_to_cart";
		pst = con.prepareStatement(s);
		rs = pst.executeQuery();
		rs.next();
		count = rs.getInt(1);
		if(count>0) {
		s = "select * from add_to_cart";
		pst = con.prepareStatement(s);
		rs = pst.executeQuery();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %22s %19s %18s  %18s %18s", "BookId", "BookName", "Author", "Price", "Category",
				"Quantity","Total_Price");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------");
	
		while (rs.next()) {
			total_price = rs.getFloat("total_price");
			System.out.format("%5d %25s %25s %12f %18s %18s %18f", rs.getInt("bookid"), rs.getString("bookname"),
					rs.getString("authorname"), rs.getFloat("bookprice"), rs.getString("category"),
					rs.getInt("quantity"),rs.getFloat("total_price"));
			System.out.println();
		}
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Place Order");
        System.out.println("1)Yes - Y");
        System.out.println("2)No  - N");
        System.out.println("Enter your choice:");
        c = sc.next().toUpperCase().charAt(0);
        if(c == 'Y') {
        	System.out.println("Enter your address:");
        	sc.nextLine();
        	address = sc.nextLine();
        	while(true) {
        	System.out.println("Enter your pincode:");
        	pincode = sc.next();
        	isValid = Validation.isValidpincode(pincode);
        	if(isValid) {
        		break;
        	}else {
        		System.out.println("Invalid pincode.");
        	}
        	}
        	while(true) {
        	System.out.println("Enter your phone number:");
        	phonenumber = sc.next();
        	isValid = Validation.isValidphonenumber(phonenumber);
        	if(isValid) {
        		break;
        	}else {
        		System.out.println("Invalid phonenumber");
        	}
        	}

        	LocalDateTime currentDate = LocalDateTime.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	orderDate = currentDate.format(formatter);
        	System.out.println("Pay Rs."+total_price);
        	grand_total+=total_price;
    		System.out.println("1)YES - Y");
    		System.out.println("2)NO - N");
    		System.out.println("Enter your choice:");
    		c = sc.next().toUpperCase().charAt(0);
    		if(c == 'Y') {
        	sql = "insert into order_table (username,email,address,pincode,phonenumber,order_date,bookid,bookname,authorname,bookprice,category,quantity,total_price)"
        			+"select ?,?,?,?,?,?,bookid,bookname,authorname,bookprice,category,quantity,total_price"
        			+ " from add_to_cart where bookid = ?";

        	pst = con.prepareStatement(sql);
        	pst.setString(1,LoginToBookStore.getUname());
        	pst.setString(2,LoginToBookStore.getUemail());
        	pst.setString(3, address);
        	pst.setString(4, pincode);
        	pst.setString(5, phonenumber);
        	pst.setString(6, orderDate);
        	pst.setInt(7, bookid);
        	int i = pst.executeUpdate();
        	
        	if(i>0) {                   //insert query order table
        		
        		System.out.println("Payment in progress...");
        		System.out.println("Payment successfull");
        		System.out.println("Order placed Successfully");
        		s = "delete from add_to_cart where bookid=?";
        		pst = con.prepareStatement(s);
        		pst.setInt(1, bookid);
        		int j = pst.executeUpdate();
        		if(j>0) {                     //delete items in cart
        			System.out.println("Thanks for your shopping!");
        			
        		}else {						
        			System.out.println("Some error occured!");
        		}
        		
        		quantity = availquantity - reqquantity;
                sql = "update book set quantity = ? where bookid = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, quantity);
                pst.setInt(2, bookid);
               int k =  pst.executeUpdate();
               if(k>0) {
            	   System.out.println("Do you want to purchase again?");
       			System.out.println("YES - Y");
       			System.out.println("Enter your choice:");
       			c = sc.next().toUpperCase().charAt(0);
       			if(c == 'Y') {            //for shopping again
       				UserOperations.addToCart();
       			}else {
       				UserOperations.getPaymentReceipt();
       			}
            	   
               }else {
            	   System.out.println("Error occured");
               }
        		
        	}else {                         //insert query to order table
        		System.out.println("Errror Occured!");
        	}
    		}else {                        //payment no means
    			UserOperations.updateCart();
    		}
        	
        }else {                           //place order no means
        	UserOperations.updateCart();
        }
		}else {
			System.out.println("*****Cart is empty****please try to add items in cart");
			
		}

	}
	
	public static void getOrderId() throws SQLException {
		con = BookStoreConnection.getConnection();
		sql = "select orderid from order_table order by orderid desc limit 1";
		pst = con.prepareStatement(sql);
		rs = pst.executeQuery();
		rs.next();
		order_id = rs.getInt("orderid");
	}

	public static void getPaymentReceipt() throws SQLException {
		con = BookStoreConnection.getConnection();
		s = "select orderid,username,address,pincode,phonenumber,bookname,authorname,bookprice,category,quantity,total_price from order_table where (orderid>?)";
		pst = con.prepareStatement(s);
		pst.setInt(1, order_id);
		rs = pst.executeQuery();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%-10s%-15s%-25s%-10s%-15s%-25s%-25s%-15s%-15s%-10s%-15s%n",
                "OrderID", "Username", "Address", "Pincode", "Phone Number",
                "Book Name", "Author Name", "Book Price", "Category", "Quantity", "Total Price");		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	
		while (rs.next()) {
			
	        System.out.format("%-10d%-15s%-25s%-10s%-15s%-25s%-25s%-15.2f%-15s%-10d%-15.2f%n",rs.getInt("orderid"), rs.getString("username"),
					rs.getString("address"),rs.getString("pincode"),rs.getString("phonenumber"),rs.getString("bookname"),rs.getString("authorname"), rs.getFloat("bookprice"),
					rs.getString("category"),rs.getInt("quantity"),rs.getFloat("total_price"));
			System.out.println();
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		
		
		System.out.println("Name             :"+LoginToBookStore.getUname());
		System.out.println("Total amount paid:RS."+grand_total);
		System.out.println("Thank you for shopping Please Vist Again!");
		
	}

}
