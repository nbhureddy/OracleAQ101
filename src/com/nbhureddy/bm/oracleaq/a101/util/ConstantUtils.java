package com.nbhureddy.bm.oracleaq.a101.util;

public interface ConstantUtils {
	public static final String QUEUE_NAME = "AQ_TEST_QUEUE";
	public static final String BUFFERED_QUEUE_NAME = "AQ_TEST_BUFFERED_QUEUE";
	
	public static final String AGENT_NAME="MSG_AGENT1";
	
	public static final String SUBSCRIBER_NAME="MSG_SBCR1";
	public static final String BUFFERED_SUBSCRIBER_NAME = "MSG_BUFFERED_SBSCR1";
	public static final String MSG_DATA_TYPE_RAW="RAW";
	
	
	public static final String JDBC_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String DB_USER = "username";
	public static final String DB_PASSWORD = "password";
}
