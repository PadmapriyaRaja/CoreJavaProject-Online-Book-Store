package com.edu.main;

import java.sql.SQLException;
import java.util.Scanner;

public class BookStoreMain {

	private static int choice;

	public static void main(String[] args) throws SQLException {

		System.out.println(">>>>> WELCOME TO THE COZY LIBRARY <<<<<");
		Scanner sc = new Scanner(System.in);
		int userType;
		boolean isLogged = false;
		while (!isLogged) { // 1
			UserOperations.getOrderId();
			System.out.println("1)LOGIN\n2)SIGN UP\n3)EXIT");
			System.out.println("Enter your choice:");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				isLogged = LoginToBookStore.check();
				if (isLogged) {
				System.out.println("Succesfully Logged in....");
				userType = UserType.user();
				if (userType == 0) {
					System.out.println("Welcome " + LoginToBookStore.getUname());
					int achoice = 0;
					char c = 'Y';
					while (true) {
					if (c == 'Y') {
					System.out.println("1)LIST OF BOOKS\n2)ADD NEW BOOKS\n3)DELETE BOOKS\n4)UPDATE\n5)VIEW ORDER HISTORY ");
					System.out.println("Enter your choice:");
					achoice = sc.nextInt();
					switch (achoice) {
					case 1:
						System.out.println("LIST OF BOOKS:");
						AdminOperations.viewBooks();
						break;
					case 2:
						System.out.println("ADDING NEW BOOKS");
						AdminOperations.insertBooks();
						break;
					case 3:
						System.out.println("DELETE BOOKS");
						AdminOperations.deleteBooks();
						break;
					case 4:
						System.out.println("UPDATE BOOKS");
						AdminOperations.update();
						break;
					case 5:
						System.out.println("ORDER HISTORY");
						AdminOperations.showHistory();
						break;
					default:
						System.out.println("****INVALID CHOICE !!!! PLEASE TRY AGAIN****");
						break;
					}
					System.out.println("Do you want to continue?");
					System.out.println("1)Yes - Y\n2)No - N");
					System.out.println("Enter your choice:");
					c = sc.next().toUpperCase().charAt(0);
					} else if (c == 'N') {
						isLogged = false;
						break;
					} else {
						System.out.println("Invalid choice");
						System.out.println("Enter your choice:");
						c = sc.next().toUpperCase().charAt(0);
					}
					}
				} else if (userType == 1) {
					System.out.println("Hi " + LoginToBookStore.getUname() + ",");
					int uchoice;
					char c = 'Y';
					while (true) {
					if (c == 'Y') {
					System.out.println("1)VIEW BOOKS\n2)ADD TO CART\n3)BUY BOOKS");
					System.out.println("Enter your Choice:");
					uchoice = sc.nextInt();
					switch (uchoice) {
					case 1:
						UserOperations.viewBooks();
						break;
					case 2:
						UserOperations.addToCart();
						break;
					case 3:
						UserOperations.buyBooks();
						break;			
					default:
						System.out.println("****INVALID CHOICE !!!! PLEASE TRY AGAIN****");
						break;
					}
					System.out.println("Do you want to continue?");
					System.out.println("1)Yes - Y\n2)No - N");
					System.out.println("Enter your choice:");
					c = sc.next().toUpperCase().charAt(0);
					} else if (c == 'N') {
						UserOperations.grand_total =0;
						isLogged = false;
						break;
					} else {
						System.out.println("Invalid choice");
						System.out.println("Enter your choice:");
						c = sc.next().toUpperCase().charAt(0);
					}
					}
				 }
				} else {
					System.out.println("Invalid credentials");
					break;
				}
				break;
			    case 2:
				   RegisterInBookStore.signup();
				   break;
			    case 3:
				   System.out.println("EXITING...");
				   System.exit(0);
				   break;
			    default:
				   System.out.println("****INVALID CHOICE !!!! PLEASE TRY AGAIN****");
				   break;
			}
		}

	}

}
