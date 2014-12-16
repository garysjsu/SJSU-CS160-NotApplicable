package com.example;

import java.sql.*;

/*
 * @author Keenan Mau
 */

public class FileMetadataDatabase {

	static final String DB_URL = "jdbc:mysql://" + System.getenv("DB_USER") + "/" + System.getenv("DB_NAME");
	static final String USER = System.getenv("DB_USER");
	static final String PASS = System.getenv("DB_PASS");

	private Connection conn = null;
	private Statement stmt = null;

	public FileMetadataDatabase() { }

	/**
	 * Database auto-increments id number, use to add Metadata. 
	 * @param name
	 * @param size
	 * @return ID number given by Database. Record to use again. 
	 */
	public FileMetadata add(String uploaderId, String name, long size) {
		openConnection();
		int id = -1;
		String update = "INSERT INTO FileMetadata (uploader_id, name, size) VALUES ('"
				+ uploaderId + "','" + name + "', " + size + ")";

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
		return new FileMetadata(id, name, size, uploaderId);
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
		String uploaderId = null;

		try {
			res = stmt.executeQuery(que);
			if (res.next()) {
				id = res.getInt("Id");
				name = res.getString("Name");
				size = res.getLong("Size");
				uploaderId = res.getString("uploader_id");
				if (res.next()) {
					System.out.println("has next");
				}
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FileMetadata stuff = new FileMetadata(id, name, size, uploaderId);
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
