package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Demo App
 */
public class App
{
	public App() {
	}

	public static void main(String[] args)
	{
		try
		{
			// Load Database driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Could not load SQL driver");
			System.exit(-1);
		}

		// Connection to the database
		Connection con = null;
		int retries = 100;
		for (int i = 0; i < retries; ++i)
		{
			System.out.println("Connecting to database...");
			try
			{
				// Wait a bit for db to start
				Thread.sleep(1000);
				// Connect to database
				con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
//				con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");

				System.out.println("Successfully connected");
				// Wait a bit
				Thread.sleep(1000);
				// Exit for loop
				break;
			}
			catch (SQLException sqle)
			{
				System.out.println("Failed to connect to database attempt " + Integer.toString(i));
				System.out.println(sqle.getMessage());
			}
			catch (InterruptedException ie)
			{
				System.out.println("Thread interrupted? Should not happen.");
			}
		}

		if (con != null)
		{

			try
			{
				// Create an SQL statement
				Statement stmt = con.createStatement();
				// Create string for SQL statement
				String strSelect = "SELECT * FROM city";
				// Execute SQL statement
				ResultSet rset = stmt.executeQuery(strSelect);
				// Return new employee if valid.
				// Check one is returned
				while (rset.next())
				{
					int ID = rset.getInt("ID");
					String name = rset.getString("Name");
					System.out.println(name);
				}
				// Close connection
				con.close();
			}
			catch (Exception e)
			{
				System.out.println("Error closing connection to database");
			}
		}
	}

	public void printCityReport(City city) {
		System.out.println(city);
	}

	public void printCityReport(ArrayList<City> cities) {

	}
}