package javaplay.db;

import java.sql.*;
import java.util.logging.Logger;

public class PostgreSQLJDBC {
	static Logger _logger = Logger.getLogger(PostgreSQLJDBC.class.getName());
	
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
	
	public PostgreSQLJDBC (String host, int port, String dbName, String username, String password) {
		_connectionString = DB_ADDRESS_PREFIX + host +":"+ port +"/"+ dbName;
		_username = username;
		_password = password;
	}
	
	
	// TODO : throw sql exception
	public void openDBConnection() {
		try {
			Class.forName(DB_DRIVER);
			_con = DriverManager.getConnection(_connectionString,_username, _password);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}
	
	// TODO : throw sql exception
	public void closeDBConnection() {
		try {
			_con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
	        		stmt.executeUpdate(sql);
	        stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
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
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
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
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}
