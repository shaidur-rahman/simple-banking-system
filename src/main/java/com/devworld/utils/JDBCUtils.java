package com.devworld.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
	public JDBCUtils() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public static Connection getConnection(String host, String port, String db, String userName, String pass) {
        try {
			return DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/%s", host, port, db), userName, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
					connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
