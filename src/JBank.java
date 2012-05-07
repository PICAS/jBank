/*
 * Zencovich Stanislav
 * 
 * Copyright (c) 2012
 * 
 * This is my first course work and the first program in java.
 * 
 * 
 * Please do not kick :) 
 */

package JustBank;

import java.io.*;
import java.sql.*;


public class JBank {
	private static MyDB DB;
	private static BufferedReader b;
	// Initialize the connection to the database
	static void init() throws SQLException
	{
		String path = "";
		String user = "";
		String password = "";
		b = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter MySQL path: ");
		try {
			path = b.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Enter MySQL user: ");
		try {
			user = b.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Enter MySQL password: ");
		try {
			password = b.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB = new MyDB(path,user,password);
	}
	// Drawing menu
	static void DrawMenu()
	{
		System.out.println("      Welcome to jBank ;)     ");
		System.out.println("==============================");
		System.out.println("1. Open a bank account.       ");
		System.out.println("2. Check bank account.        ");
		System.out.println("3. Close the bank account.    ");
		System.out.println("4. Deposit bank account:      ");
		System.out.println("5. Withdrawal of bank account:");
		System.out.println("6. Transfer of funds.         ");
		System.out.println("7. All bank accounts.         ");
		System.out.println("==============================");
		System.out.println("0. Exit.                      ");
		System.out.print("Your action: ");
	}
	public static void main(String [] args) throws SQLException
	{
		// Initialize the connection
		init();
		// Check the status and implementation of appropriate action
		if (DB.result)
		{
			String text;	//	The variable for temporary storage of text
			String User, Password;
			String UserFrom, UserPassword, UserTo;
			Double Money;
			int count = -1;	//	Number of the selected item
			
			try 
			{
				//	Until then, until you select "Exit"
				while (count != 0)
				{
					DrawMenu();
					text = b.readLine();
					count = Integer.parseInt(text);
					
					switch (count)
					{
					case 1:	//	Open a bank account
						System.out.print("Enter your name: ");
						User = b.readLine();
						System.out.print("Enter your password: ");
						Password = b.readLine();
						System.out.print("Enter the sum of: ");
						text = b.readLine();
						Money = Double.parseDouble(text);
						DB.CreateUser(User, Password, Money);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 2:	//	Check bank account
						System.out.print("Enter your name: ");
						User = b.readLine();
						System.out.print("Enter your password: ");
						Password = b.readLine();
						Money = DB.GetMoney(User,Password);
						if (Money != MyDB.WrongPassword)
							System.out.printf("You have %f money in the bank.\n",Money);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 3:	//	Close the bank account
						System.out.print("Enter your name: ");
						User = b.readLine();
						System.out.print("Enter your password: ");
						Password = b.readLine();
						DB.DeleteUser(User, Password);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 4:	//	Deposit bank account
						System.out.print("Enter your name: ");
						User = b.readLine();
						System.out.print("Enter your password: ");
						Password = b.readLine();
						System.out.print("Enter the sum of: ");
						text = b.readLine();
						Money = Double.parseDouble(text);
						DB.DepositMoney(User, Password, Money);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 5:	//	Withdrawal of bank account
						System.out.print("Enter your name: ");
						User = b.readLine();
						System.out.print("Enter your password: ");
						Password = b.readLine();
						System.out.print("Enter the sum of: ");
						text = b.readLine();
						Money = Double.parseDouble(text);
						DB.DepositMoney(User, Password, -Money);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 6:	//	Transfer of funds
						System.out.print("Enter the name of the sender: ");
						UserFrom = b.readLine();
						System.out.print("Enter the password sender: ");
						UserPassword = b.readLine();
						System.out.print("Enter the name of the recipient: ");
						UserTo = b.readLine();
						System.out.print("Enter the sum of: ");
						text = b.readLine();
						Money = Double.parseDouble(text);
						DB.TransferMoney(UserFrom,UserPassword,UserTo,Money);
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 7:	//	All bank accounts
						DB.GetUserMoney();
						System.out.print("Press any key..");
						b.readLine();
						break;
					case 0: //	Exit
						return;
					default:
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			System.out.println("MySQL not connected!");
	}
}