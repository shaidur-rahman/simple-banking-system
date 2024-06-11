package com.devworld.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
	private static Connection connection;

	// Preventing object instantiation in-order to provide same connection all over the application
	private JDBCUtils() {
	}

	private static void registerDriver() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver manager class registered.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void initialize(String host, String port, String db, String userName, String pass) {
		try {
			if (connection != null && !connection.isClosed()) return;
			registerDriver();
			System.out.println("Creating database connection...");
			connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/%s", host, port, db), userName, pass);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public static Connection getConnection() {
		return connection;
	}
}
