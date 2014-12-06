package com.example;

import java.sql.*;

/*
 * @author Keenan Mau
 * trying to do Java connection with MySQL. Plz help if you see me doing something wrong. 
 */

public class MySQLconnectorJavaBean { // Database name: FileMetadata
	// STEP 1. Import required packages

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "tree";

	private Connection conn = null;
	private Statement stmt = null;

	public MySQLconnectorJavaBean() {//
		// public static void main(String[] args) {
		conn = null;
		stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, first, last, age FROM Employees";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Age: " + age);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}// end main

	public void add(FileMetadata a) {
		String update = "INSERT INTO FileMetadata (id, name, size)"
				+ "VALUES (" + a.getId() + ", " + a.getName() + ", " + a.getSize() + ")";
		System.out.print("    Update: ");
		int rowCount = 0;
		try {
			rowCount = stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(rowCount + " rows changed.");

	}

	public void remove(int ID) {
		String delete = "DELETE FROM FileMetadata" + "WHERE first '" + ID + "'";
		System.out.print("    Delete: ");
		int rowCount = 0;
		try {
			rowCount = stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rowCount + " rows removed.");

	}

	public FileMetadata getData(int ID) {
		return null;
	}
}// end FirstExample

