package com.nbhureddy.bm.oracleaq.a101.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector implements ConstantUtils {
	
	private static Connection connection = null;
	
	public static Connection getDataBaseConnection() {
		if(connection == null) {
			connection = initializeConnection();
		}
		return connection;
	}
	
	
	public static Connection initializeConnection() {
		Connection connection = null;
		try {
			Class.forName(JDBC_DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * Utility method: executes a DML query but doesn't throw any exception if
	 * an error occurs.
	 */
	public static void executeStatement(String sql) {
		Statement stmt = null;
		try {
			Connection connection = getDataBaseConnection();
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException sqlex) {
			System.out.println("Exception (" + sqlex.getMessage() + ") while trying to execute \"" + sql + "\"");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException exx) {
					exx.printStackTrace();
				}
			}
		}
	}
}
