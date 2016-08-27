package javaplay.db;

import java.sql.*;
import java.util.logging.Logger;

/**
 *  This is a db access handler for connection management and to executing sql queries
 */
public class DBHandler {
	// Logging
	static Logger _logger = Logger.getLogger(DBHandler.class.getName());
	
	// Constants for db access
	public static final String DB_DRIVER = "org.postgresql.Driver";
	public static final String DB_ADDRESS_PREFIX = "jdbc:postgresql://";
	public static final String DB_DEFAULT_HOST = "localhost";
	public static final int DB_DEFAULT_PORT = 5432;
	public static final String DB_DEFAULT_NAME = "testdb";
	public static final String DB_DEFAULT_USER = "postgres";
	public static final String DB_DEFAULT_PASSWORD = "admin";
	
	// Class members
	private String _connectionString;
	private String _username;
	private String _password;
	private Connection _con;
	
	// Object constructor
	public DBHandler (String host, int port, String dbName, String username, String password) {
		_connectionString = DB_ADDRESS_PREFIX + host +":"+ port +"/"+ dbName;
		
		_username = username;
		_password = password;
	}
	
	/**
	 * Open a db connection
	 * @return True if a connection was set, false otherwise
	 * @throws SQLException - SQLException if a database access error occurs
	 */
	public boolean openDBConnection() throws SQLException {
		//Class.forName(DB_DRIVER); - TODO: check if relevant or dead code
		if (_con == null || _con.isClosed()) {
			_con = DriverManager.getConnection(_connectionString,_username, _password);
			return true;
		}
		return false;
	}
	
	/** 
	 * Close db connection if opened
	 * @return True if close was set, false otherwise 
	 * @throws SQLException - SQLException if a database access error occurs
	 */
	public boolean closeDBConnection() throws SQLException {
		if (_con != null && !_con.isClosed()) {
			_con.close();
			return true;	
		}
		return false;
	}
	
	/**
	 * Executes the given SQL statement, which returns a single ResultSet object. 
	 * <br><b>Note:</b> This method cannot be called on a PreparedStatement or CallableStatement
	 * @param sql - an SQL statement to be sent to the database, <u>typically a static SQL SELECT statement</u>
	 * @return a ResultSet object that contains the data produced by the given query; never null
	 * @throws SQLException - if a database access error occurs, this method is called on a closed Statement,
	 * the given SQL statement produces anything other than a single ResultSet object,
	 * the method is called on a PreparedStatement or CallableStatement
	 */
	public ResultSet exceute(String sql) throws SQLException {		
		Statement stmt = _con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		stmt.close();
        
		return rs;
	}
	
	/**
	 * Execute sql and return the manipulated rows count
	 * @param sql - an SQL Data Manipulation Language (DML) statement,<br>
	 * such as <u>INSERT, UPDATE or DELETE;</u> or an SQL statement that returns nothing, such as a DDL statement.
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or<br>
	 * (2) the value 0 for SQL statements that return nothing
	 * @throws SQLException - if a database access error occurs, this method is called on a closed Statement,
	 * the given SQL statement produces a ResultSet object,
	 * the method is called on a PreparedStatement or CallableStatement
	 */
	public int executeUpdate(String sql) throws SQLException {
		Statement stmt = _con.createStatement();
		int count = stmt.executeUpdate(sql);
		stmt.close();

		return count;
	}
	
	/**
	 * create user table
	 * @throws SQLException
	 */
	public void createTableUsers() throws SQLException {
		Statement stmt = _con.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS USERS " +
				"(ID	SERIAL  NOT NULL PRIMARY	KEY," + 
				" USER_NAME	TEXT	NOT	NULL, " +
				" PASSWORD	TEXT	NOT NULL, " +
				" EMAIL	TEXT	NOT NULL)";
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	/**
	 * add x number of demo users
	 * @param count for number of users
	 * @throws SQLException
	 */
	public void addUsers(int count) throws SQLException {
		String sql = "INSERT INTO USERS(USER_NAME, PASSWORD, EMAIL) VALUES(?, ?, ?)";
		PreparedStatement ps = _con.prepareStatement(sql);
		
		for (int i = 0; i < count; i++) {
			ps.setString(1, "User" + i);
			ps.setString(2, "Pass" + i);
			ps.setString(3, "user" + i +"@mail.com");
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	/**
	 * clear user table
	 * @throws SQLException
	 */
	public void dropUsers() throws SQLException {
		Statement stmt = _con.createStatement(); 
		String sql = "DROP TABLE USERS";
		stmt.executeUpdate(sql);
		stmt.close();
	}
}
