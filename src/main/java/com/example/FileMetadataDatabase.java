package com.example;

import java.sql.*;

/*
 * @author Keenan Mau
 */

public class FileMetadataDatabase { 
	// Database name: FileMetadata

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "tree";
	static int IDcount = 0;

	private Connection conn = null;
	private Statement stmt = null;


	public FileMetadataDatabase() {
	}

	/**
	 * Should not use as Database automatically increments id counter. 
	 * @param a
	 */
	public void add(FileMetadata a) { 
		openConnection();
		String update = "INSERT INTO FileMetadata " + "VALUES (" + a.getId()
				+ ", '" + a.getName() + "', " + a.getSize() + ")";
		System.out.print("    Update: ");
		int rowCount = 0;
		try {
			rowCount = stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rowCount + " rows changed.");
		closeConnection();
	}

	/**
	 * Database auto-increments id number, use to add Metadata. 
	 * @param name
	 * @param size
	 * @return ID number given by Database. Record to use again. 
	 */
	public int add(String name, long size) { 
		openConnection();
		int id = -1;
		String update = "INSERT INTO FileMetadata (name, size) VALUES ('"
				+ name + "', " + size + ")";
		System.out.print("    Update: ");
		ResultSet res = null;
		int rowCount = 0;
		try {
			rowCount = stmt.executeUpdate(update);
			res = stmt.executeQuery("select last_insert_id()");
			res.next();
			id = res.getInt("last_insert_id()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rowCount + " rows changed.");
		closeConnection();
		return id;
	}

	/**
	 * Deletes Metadata in the database. 
	 * @param ID
	 */
	public void remove(int ID) { 
		openConnection();
		String delete = "DELETE FROM FileMetadata WHERE id = " + ID;
		System.out.print("    Delete: ");
		int rowCount = 0;
		try {
			rowCount = stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rowCount + " rows removed.");
		closeConnection();
	}
	
	/**
	 * Given the id
	 * @param ID
	 * @return a FileMetadata object containing all the info, ID, name, and size. 
	 */
	public FileMetadata getData(int ID) {
		openConnection();
		String que;
		que = "SELECT * FROM FileMetadata WHERE id = " + ID;
		ResultSet res = null;
		int id = 0;
		String name = null;
		long size = 0;
		try {
			res = stmt.executeQuery(que);
			if (res.next()) {
				id = res.getInt("Id");
				name = res.getString("Name");
				size = res.getLong("Size");
				if (res.next()) {
					System.out.println("has next");
				}
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FileMetadata stuff = new FileMetadata(id, name, size);
		closeConnection();
		return stuff;
	}

	private void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se2) {
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
