package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garindraprahandono on 12/10/14.
 */
public class DownloadTracker {

    private static DownloadTracker instance;

    // JDBC driver name and database URL
    static final String DB_URL = "jdbc:mysql://localhost/EMP";
    // Database credentials
    static final String USER = "root";
    static final String PASS = ""; //"tree";
    private Connection conn = null;
    private Statement stmt = null;

    public void trackDownload(int fileId, String userAgent, String ipAddress) {

        String update = "INSERT INTO FileDownloads (file_id, user_agent, ip_address) VALUES ("
                + fileId + ",'" + userAgent + "', '" + ipAddress + "')";

        System.out.print("    Update: ");
        ResultSet res = null;
        int rowCount = 0;
        try {
            rowCount = stmt.executeUpdate(update);
            res = stmt.executeQuery("select last_insert_id()");
            res.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("TRACKING " + userAgent + " " + ipAddress);
    }

    public List<Download> getDownloads(int fileId) {

        String que;
        que = "SELECT * FROM FileDownloads WHERE file_id = " + fileId;
        ResultSet res = null;
        int id = 0;
        String userAgent = null;
        String ipAddress = null;

        List<Download> downloads = new ArrayList<Download>();

        try {
            res = stmt.executeQuery(que);
            while (res.next()) {
                fileId = res.getInt("file_id");
                userAgent = res.getString("user_agent");
                ipAddress = res.getString("ip_address");

                downloads.add(new Download(fileId, userAgent, ipAddress));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return downloads;
    }

    public void connect() {

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

    public class Download {

        public int fileId;
        public String userAgent;
        public String ipAddress;

        public Download(int fileId, String userAgent, String ipAddress) {
            this.fileId = fileId;
            this.userAgent = userAgent;
            this.ipAddress = ipAddress;
        }
    }

    public static DownloadTracker getInstance() {

        if (instance != null) {
            return instance;
        }

        instance = new DownloadTracker();
        instance.connect();

        return instance;
    }
}
