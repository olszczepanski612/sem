package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

// test
public class App {

	private Connection con = null;

	// Starting point of the program
	public static void main(String[] args) {
		App app = new App();

		String location = args.length > 0
				? args[0] : "localhost:33060";

		int delay = args.length > 1
				? Integer.parseInt(args[1]) : 30000;

		try {
			app.connect(location, delay);

			City city = app.getCity(1);
			if (city != null) {
				app.printCityReport(city);
			}
		} finally {
			app.disconnect();
		}
	}

	// Connect to database
	public void connect(String location, int delay) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"Could not load MySQL driver", e);
		}

		String url = "jdbc:mysql://" + location
				+ "/world?useSSL=false"
				+ "&allowPublicKeyRetrieval=true";

		long deadline = System.currentTimeMillis() + delay;
		SQLException lastError = null;

		do {
			try {
				System.out.println("Connecting to database...");

				con = DriverManager.getConnection(
						url, "root", "example");

				System.out.println("Successfully connected");
				return;

			} catch (SQLException e) {
				lastError = e;
				System.out.println(
						"Database not ready: " + e.getMessage());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					throw new IllegalStateException(
							"Connection interrupted", ie);
				}
			}
		} while (System.currentTimeMillis() < deadline);

		throw new IllegalStateException(
				"Could not connect to database", lastError);
	}

	// Get the details of a specific city using the id
	public City getCity(int id) {
		if (con == null) {
			throw new IllegalStateException(
					"Database is not connected");
		}

		String sql = "SELECT * FROM city WHERE ID = ?";

		try (PreparedStatement stmt =
					 con.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new City(
							rs.getLong("ID"),
							rs.getString("Name"),
							rs.getString("CountryCode"),
							rs.getString("District"),
							rs.getLong("Population")
					);
				}
			}

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Error retrieving city", e);
		}

		return null;
	}

	// Disconnect from database
	public void disconnect() {
		if (con != null) {
			try {
				con.close();
				con = null;
				System.out.println("Database disconnected");
			} catch (SQLException e) {
				throw new IllegalStateException(
						"Error closing database", e);
			}
		}
	}

	// Print out a report on a city
	public void printCityReport(City city) {
		System.out.println(city);
	}

	// If argument is an array, print out a report on all the listed cities
	public void printCityReport(ArrayList<City> cities) {
		for (City city : cities) {
			printCityReport(city);
		}
	}
}
