package com.devworld.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.devworld.utils.JDBCUtils;

public class TestJDBCUtils {
	private static Connection connection;
	private static final String HOST = "192.168.0.130";
	private static final String PORT = "5432";
	private static final String DATABASE = "postgres";
	private static final String USER = "postgres";
	private static final String PASS = "postgres";

	@BeforeAll
	static void setup() {
		connection = JDBCUtils.getConnection(HOST, PORT, DATABASE, USER, PASS);
	}

	@Test
	void test() {
		fail("Not yet implemeted!");
	}
	
	@AfterAll
	static void cleanUp() {
		JDBCUtils.closeConnection(connection);
	}
}
