package javaplay.db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
	static Logger _logger = Logger.getLogger(DBHandler.class.getName());
	
	public static final String DB_DRIVER = "org.postgresql.Driver";
	public static final String DB_ADDRESS_PREFIX = "jdbc:postgresql://";
	public static final String DB_DEFAULT_HOST = "localhost";
	public static final int DB_DEFAULT_PORT = 5432;
	public static final String DB_DEFAULT_NAME = "testdb";
	public static final String DB_DEFAULT_USER = "postgres";
	public static final String DB_DEFAULT_PASSWORD = "admin";
	
	private String _connectionString;
	private String _username;
	private String _password;
	private Connection _con;
	
	// TODO: implement all other connection methods
	public DBHandler (String host, int port, String dbName, String username, String password) {
		/*
		 * Please use string format
		 *	http://alvinalexander.com/blog/post/java/use-string-format-java-string-output
		 */
		
		_connectionString = DB_ADDRESS_PREFIX + host +":"+ port +"/"+ dbName;
		_username = username;
		_password = password;
	}
	
	
	// TODO : throw sql exception, check before open
	public void openDBConnection() {
		try {
			Class.forName(DB_DRIVER);
			_con = DriverManager.getConnection(_connectionString,_username, _password);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.log(Level.SEVERE, e.getClass().getName()+": "+ e.getMessage(), e);
			System.exit(0);
		}
		
	}
	
	// TODO : throw sql exception, check before close
	public void closeDBConnection() {
		try {
			_con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			_logger.log(Level.SEVERE, e.getClass().getName()+": "+ e.getMessage(), e);
			System.exit(0);
		}
	}
	
	// Execute sql and return the result set
	public ResultSet exceute(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = _con.createStatement();
			rs = stmt.executeQuery(sql);
	        stmt.close();
		} catch ( Exception e ) {
			_logger.log(Level.SEVERE, e.getClass().getName()+": "+ e.getMessage(), e);
			System.exit(0);
		}
		return rs;
	}
	
	// Execute sql and return the manipulated rows count
	public int executeUpdate(String sql) {
		Statement stmt = null;
		int count = -1;
		try {
			stmt = _con.createStatement();
			count = stmt.executeUpdate(sql);
	        stmt.close();
		} catch ( Exception e ) {
			_logger.log(Level.SEVERE, e.getClass().getName()+": "+ e.getMessage(), e);
			System.exit(0);
		}
		return count;
	}
	
	public void createTableUsers() {
		Statement stmt = null;
		try {
			stmt = _con.createStatement();
	        String sql = "CREATE TABLE USERS " +
	                     "(ID	INT	PRIMARY	KEY	NOT	NULL," +
	                     " USER_NAME	TEXT	NOT	NULL, " +
	                     " PASSWORD	TEXT	NOT NULL, " +
	                     " EMAIL	TEXT	NOT NULL)";
	        stmt.executeUpdate(sql);
	        stmt.close();
		} catch ( Exception e ) {
			_logger.log(Level.SEVERE, e.getClass().getName()+": "+ e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}
