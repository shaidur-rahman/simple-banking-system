package com.devworld.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.devworld.utils.JDBCUtils;

public class BaseTestConfig {
	private static Connection connection;
	private static final String HOST = "192.168.0.130";
	private static final String PORT = "5432";
	private static final String DATABASE = "sbs";
	private static final String USER = "postgres";
	private static final String PASS = "postgres";

	@BeforeAll
	static void setup() {
		JDBCUtils.initialize(HOST, PORT, DATABASE, USER, PASS);
		connection  = JDBCUtils.getConnection();
	}

	@Test
	void test() {
		assertNotNull(connection, "Could not initialize database connection!");
		try {
			assertFalse(connection.isClosed(), "Connection already closed!");
		} catch (SQLException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void cleanUp() {
		JDBCUtils.closeConnection(connection);
	}
}
