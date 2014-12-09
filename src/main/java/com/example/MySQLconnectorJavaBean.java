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
	static int IDcount = 0;

	private Connection conn = null;
	private Statement stmt = null;

	public static void main(String[] args) {
		MySQLconnectorJavaBean a = new MySQLconnectorJavaBean();
		// FileMetadata d1 = new FileMetadata(101, "pic1", 1234567890);
		System.out.println("" + a.add("pic1", 1234567899));
		System.out.println("" + a.add("kitten", 1111122));
		System.out.println("" + a.add("puppy", 33442542));
		System.out.println(a.getData(1).toString());
		//a.remove(5);

	}

	public MySQLconnectorJavaBean() {
	}

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

	public int add(String name, long size) { // aaaaaaaaaaaaaaaaaaaaaa
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

	public void remove(int ID) { // MAKE WAY FOR THE QUEEEEEEEEEEEEN
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
