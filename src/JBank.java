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

/*
 * MyDB - A class for working with database jBank ;)
 * Version 0.1 alpha
 * Copyright JustCrazy
 */

class MyDB{
	private Statement stmt;
	private Connection conn;
	public Boolean result;	// Status of the connection to the database
	static double WrongPassword = 1.7E+308;	// A constant that serves to identify an incorrect password
	// Connecting to a MySQL database
	MyDB(String path, String user, String password) throws SQLException
	{
		try
		{
			System.out.printf("Connect to: %s by user '%s'.. ", path,user);
			conn = DriverManager.getConnection(path,user,password);
	 
	        if (conn == null)
	        {
	            System.out.println("No database connection!");
	            result = false;
	        }
	        
	        stmt = conn.createStatement();
	        System.out.println("Connected!");
	        result = true;
		} catch (SQLException ex)
		{
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    result = false;
		}
	}
	// Creating records in the database
	public Boolean CreateUser(String User, String Password, double Money)
	{
		if (result)
		{
			System.out.printf("Opening a bank account with username %s %f sum.. ", User,Money);
				try
				{
					// Perform a query to the database
						if (stmt.executeUpdate("INSERT ACCOUNTS VALUES ('"+User+"','"+Password+"','"+Money+"')") == 1)
						{
							System.out.println("OK!");
							return true;
						}
						else
						{
							System.out.println("ERROR!");
							return false;
						}
				}
				catch (SQLException ex)
				{
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}
		}
		else
			System.out.println("No database connection!");
		return false;
	}
	// Delete a record to the database
	public Boolean DeleteUser(String User, String Password)
	{
		if (result)
		{
			System.out.printf("Deleting user account %s.. ", User);
				try
				{
					// Check the data on correctness
					if (GetPassword(User).equals(Password))
					{
						// Perform a query to the database
						if (stmt.executeUpdate("DELETE FROM ACCOUNTS WHERE user = '"+User+"'") == 1)
						{
							System.out.println("OK!");
							return true;
						}
						else
						{
							System.out.println("ERROR!");
							return false;
						}
					}
					else
					{
						System.out.println("Wrong password!");
						return false;
					}
				}
				catch (SQLException ex)
				{
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}
		}
		else
			System.out.println("No database connection!");
		return false;
	}
	// Getting all the information from the database (Money+User)
	public void GetUserMoney() throws SQLException
	{
		if (result)
		{
			try
			{
				// Perform a query to the database
				ResultSet rs = stmt.executeQuery("SELECT * FROM ACCOUNTS");
				 
		        while (rs.next())
		        {
		            System.out.println(rs.getRow() + ". " + rs.getString("user")
		                    + "\t" + rs.getString("money"));
		        }
			}
			catch (SQLException ex)
			{
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		else
			System.out.println("No database connection!");
	}
	// Obtaining selected information from the database (Money)
	public double GetMoney(String User, String Password) throws SQLException
	{
		if (result)
		{
			try
			{
				// Check the data on correctness
				if (GetPassword(User).equals(Password))
				{
					// Perform a query to the database
					ResultSet rs = stmt.executeQuery("SELECT money FROM ACCOUNTS WHERE user = '"+User+"' AND password = '"+Password+"'");
				 
					while (rs.next())
					{
						return rs.getDouble(1);
					}
				}
				else
				{
					System.out.println("Wrong password!");
					return WrongPassword;
				}
			}
			catch (SQLException ex)
			{
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		else
			System.out.println("No database connection!");
		return 0;
	}
	// Obtaining selected information from the database (User password)
	public String GetPassword(String User) throws SQLException
	{
		if (result)
		{
			try
			{
				// Perform a query to the database
				ResultSet rs = stmt.executeQuery("SELECT password FROM ACCOUNTS WHERE user = '"+User+"'");
				
				while (rs.next())
		        {
						return rs.getString(1);
		        }
			}
			catch (SQLException ex)
			{
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		else
			System.out.println("No database connection!");
		return "";
	}
	// Change selected information from the database (Deposit/Withdrawal Money)
	public Boolean DepositMoney(String User, String Password, double Money) throws SQLException
	{
		if (result)
		{
			System.out.printf("Deposit Account '%s' in the amount of %f.. ", User,Money);
			double rez = GetMoney(User,Password);
			// Check for money
			if (((Money > 0) || ((rez >= -Money) && (Money < 0)))
				&& (rez != WrongPassword))
			{
				try
				{
					// Perform a query to the database
					if (stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(GetMoney(User,Password)+Money)+" WHERE user = '"+User+"'") == 1)
					{
						System.out.println("OK!");
						return true;
					}
					else
					{
						System.out.println("ERROR!");
						return false;
					}
				}
				catch (SQLException ex)
				{
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}
			}
			else
				if (rez != WrongPassword)
					System.out.println("The user '"+User+"' has insufficient funds in your bank account!");
			return false;
		}
		else
			System.out.println("No database connection!");
		return false;
	}
	// Change selected information from the database (Money Transfer)
	public Boolean TransferMoney(String UserFrom, String Password, String UserTo, double Money) throws SQLException
	{
		if (result)
		{
			System.out.printf("Performing the transfer of funds in the amount of %f from %s to %s.. ", Money, UserFrom, UserTo);
			double rez = GetMoney(UserFrom,Password);
			// Check for money
			if ((rez >= Money) && (rez != WrongPassword))
			{
				try
				{
					// Perform a query to the database
					if (stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(GetMoney(UserFrom,Password)-Money)+" WHERE user = '"+UserFrom+"'") == 1)
					{
						// // Perform a query to the database
						if (stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(GetMoney(UserTo,GetPassword(UserTo))+Money)+" WHERE user = '"+UserTo+"'") == 1)
						{
							System.out.println("OK!");
							return true;
						}
						else
						{
							// Run the query fails, a rollback
							stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(GetMoney(UserFrom,Password)+Money)+" WHERE user = '"+UserFrom+"'");
							System.out.println("ERROR!");
							return false;
						}
					}
					else 
					{
						System.out.println("ERROR!");
						return false;
					}
				}
				catch (SQLException ex)
				{
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}
			}
			else
				if (rez != WrongPassword)
					System.out.println("The user '"+UserFrom+"' has insufficient funds in your bank account!");
			return false;
		}
		else
			System.out.println("No database connection!");
		return false;
	}
}

public class JBank {
	private static MyDB DB;
	private static BufferedReader b;
	// Initialize the connection to the database
	static void init() throws SQLException
	{
		String path = "jdbc:mysql://mysql-srv26023.ht-systems.ru:3306/srv26023_java";
		String user = "srv26023_java";
		String password = "java_test";
		b = new BufferedReader(new InputStreamReader(System.in));
		/*System.out.print("Enter MySQL path: ");
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
		}*/
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
	init();
	if (DB.result)
	{
		String text;
		String User, Password;
		String UserFrom, UserPassword, UserTo;
		Double Money;
		int count = -1;
		try 
		{
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
					if (Money != 1.7E+308)
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