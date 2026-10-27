
package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
	/**
	 * Connection to MySQL database.
	 */
	private Connection con = null;

	/**
	 * Connect to the MySQL world database.
	 */
	public boolean connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Could not load SQL driver");
			return false;
		}

		int retries = 60;

		for (int i = 0; i < retries; i++)
		{
			System.out.println("Connecting to database...");

			try
			{
				con = DriverManager.getConnection(
						"jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false",
						"root",
						"example"
				);

				System.out.println("Successfully connected");
				return true;
			}
			catch (SQLException e)
			{
				System.out.println("Database not ready: " + e.getMessage());

				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException ie)
				{
					Thread.currentThread().interrupt();
					return false;
				}
			}
		}

		System.out.println("Could not connect after all retry attempts.");
		return false;
	}

	/**
	 * Disconnect from the MySQL database.
	 */
	public void disconnect()
	{
		if (con != null)
		{
			try
			{
				con.close();
				con = null;
			}
			catch (SQLException e)
			{
				System.out.println("Error closing connection to database");
			}
		}
	}

	/**
	 * Display city details.
	 */
	public void displayCity(City city)
	{
		if (city != null)
		{
			System.out.println(
					"City ID: " + city.ID + "\n"
							+ "Name: " + city.Name + "\n"
							+ "Country Code: " + city.CountryCode + "\n"
							+ "District: " + city.District + "\n"
							+ "Population: " + city.Population + "\n"
			);
		}
		else
		{
			System.out.println("City not found.");
		}
	}

	/**
	 * Get a city by its ID.
	 */
	public City getCity(int ID)
	{
		String strSelect =
				"SELECT ID, Name, CountryCode, District, Population "
						+ "FROM city "
						+ "WHERE ID = ?";

		try (PreparedStatement stmt = con.prepareStatement(strSelect))
		{
			stmt.setInt(1, ID);

			try (ResultSet rset = stmt.executeQuery())
			{
				if (rset.next())
				{
					City city = new City();

					city.ID = rset.getInt("ID");
					city.Name = rset.getString("Name");
					city.CountryCode = rset.getString("CountryCode");
					city.District = rset.getString("District");
					city.Population = rset.getInt("Population");

					return city;
				}
				else
				{
					return null;
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Failed to get city details");
			return null;
		}
	}

	/**
	 * Print a report for a single city.
	 */
	public void printCityReport(City city)
	{
		displayCity(city);
	}

	/**
	 * Print a report for a list of cities.
	 */
	public void printCityReport(ArrayList<City> cities)
	{
		if (cities == null || cities.isEmpty())
		{
			System.out.println("No cities to display.");
			return;
		}

		for (City city : cities)
		{
			printCityReport(city);
		}
	}

	/**
	 * Main application.
	 */
	public static void main(String[] args)
	{
		App a = new App();

		if (a.connect())
		{
			try
			{
				City city = a.getCity(1);
				a.displayCity(city);
			}
			finally
			{
				a.disconnect();
			}
		}
		else
		{
			System.out.println(
					"Application stopped: database connection failed."
			);
		}
	}
}
