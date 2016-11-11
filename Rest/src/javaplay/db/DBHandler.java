package javaplay.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.json.simple.parser.ParseException;

import javaplay.utils.Keys;

/**
 *  This is a db access handler for connection management and to executing sql queries
 */
public class DBHandler {
	// Logging
	static Logger _logger = Logger.getLogger(DBHandler.class.getName());
	
	// Constants for db access
	public static final String DB_DRIVER = "org.postgresql.Driver";
	
	// Class members
	private String _connectionString;
	private String _username;
	private String _password;
	private Connection _con;
	
	/**
	 *  DBHandler constructor 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public DBHandler () throws ClassNotFoundException, FileNotFoundException, IOException, ParseException {
		//init();
		
		Map<String, String> db_keys = Keys.getMap("db_connection");
		
		_connectionString = String.format("jdbc:%s://%s/%s?user=%s&password=%s", 
				db_keys.get("driver"),
				db_keys.get("host"),
				db_keys.get("database"),
				db_keys.get("user"),
				db_keys.get("password"));
	}
	
	/**
	 * Initialize db driver
	 * @throws ClassNotFoundException - if the db driver class cannot be located
	 */
	private void init() throws ClassNotFoundException {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException ex){
			_logger.log(Level.SEVERE, "Error: unable to load db driver class \"" + DB_DRIVER + "\"");
			throw ex;
		}
	}
	
	/**
	 * Open a db connection
	 * @return True if a connection was set, false otherwise
	 * @throws SQLException - if a database access error occurs
	 */
	private boolean openDBConnection() throws SQLException {
		if (_con == null || _con.isClosed()) {
			_con = DriverManager.getConnection(_connectionString);
			return true;
		}
		return false;
	}
	
	/** 
	 * Close db connection if opened
	 * @return True if close was set, false otherwise 
	 * @throws SQLException - SQLException if a database access error occurs
	 */
	private boolean closeDBConnection() throws SQLException {
		if (_con != null ) {
			_con.close();
			return true;
		}
		return false;
	}
	
	/**
	 * Executes the given SQL statement, which returns a single ResultSet object. 
	 * <br><b>Note:</b> This method cannot be called on a PreparedStatement or CallableStatement
	 * @param sql - an SQL statement to be sent to the database, <u>typically a static SQL SELECT statement</u>
	 * @return a ResultSet object that contains the data produced by the given query; never null, based on :<br>
	 * http://stackoverflow.com/questions/1910049/where-to-close-a-jdbc-connection-while-i-want-to-return-the-resultset<br>
	 * http://stackoverflow.com/questions/14853508/returning-a-resultset<br>
	 * http://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc<br>
	 * http://stackoverflow.com/questions/8217493/implementations-of-rowset-cachedrowset-etc
	 * @throws SQLException - if a database access error occurs, this method is called on a closed Statement,
	 * the given SQL statement produces anything other than a single ResultSet object,
	 * the method is called on a PreparedStatement or CallableStatement
	 */
	public ResultSet exceute(String sql) throws SQLException {		
		// Using RowSetFactory for CachedRowSet implementation
		RowSetFactory rowSetFactory = RowSetProvider.newFactory();
		
		Statement stmt = null;
		ResultSet rs = null;
		CachedRowSet crs = rowSetFactory.createCachedRowSet();;
		
		try {
			openDBConnection();
			stmt = _con.createStatement();
			rs = stmt.executeQuery(sql);
			crs.populate(rs);
		} finally {
			if (stmt != null) {stmt.close();}
			if (rs != null) {rs.close();}
			closeDBConnection();
		}
		return crs;
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
		Statement stmt = null;
		int manipulatedRowCount = -1;
		
		try {
			openDBConnection();
			stmt = _con.createStatement();
			manipulatedRowCount = stmt.executeUpdate(sql);			
		} finally {
			if (stmt != null) {stmt.close();}
			closeDBConnection();
		}

		return manipulatedRowCount;
	}
	
	/**
	 * create user table
	 * @throws SQLException
	 */
	public void createTableUsers() throws SQLException {
		Statement stmt = null; 
		try {
			openDBConnection();
			stmt = _con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS USERS " +
					"(ID	SERIAL  NOT NULL PRIMARY	KEY," + 
					" USER_NAME	TEXT	NOT	NULL, " +
					" PASSWORD	TEXT	NOT NULL, " +
					" EMAIL	TEXT	NOT NULL)";
			stmt.executeUpdate(sql);
		} finally {
			if (stmt != null) {stmt.close();}
			closeDBConnection();
		}
	}
	
	/**
	 * Adds login audit into databases
	 * @param loginDate
	 * @param ip
	 * @param deviceId
	 * @throws SQLException
	 */
	public void addLogin(Date loginDate, String ip, String deviceId) throws SQLException {
		PreparedStatement insertLogin = null;
		
		try {
			openDBConnection();
			String sql = "INSERT INTO LOGIN_EVENTS (LOGIN_DATE, IP, DEVICE_ID) VALUES (?, ?, ?)";
			insertLogin = _con.prepareStatement(sql);
			
        	insertLogin.setDate(1, java.sql.Date.valueOf(loginDate.toString()));
        	insertLogin.setString(2, ip);
        	insertLogin.setString(3, deviceId);

			insertLogin.executeBatch();
		} finally {
			if (insertLogin != null) {insertLogin.close();}
			closeDBConnection();
		}
	}

	public ResultSet loadLogins(int count) throws SQLException {	
		String sql = String.format("SELECT * FROM LOGIN_EVENTS ORDER BY LOGIN_DATE LIMIT %d", count);
		
		return exceute(sql);
	}
}
