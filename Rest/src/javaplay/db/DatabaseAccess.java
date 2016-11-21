package javaplay.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;
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
public class DatabaseAccess {
	/**
	 * Static 
	 */
	private static Logger _logger = Logger.getLogger(DatabaseAccess.class.getName());
	private static DatabaseAccess mInstance;
	
	/**
	 * Members
	 */
	private String _connectionString;
	private Connection _con;
	
	/**
	 *  DBHandler constructor 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private DatabaseAccess () throws ClassNotFoundException, FileNotFoundException, IOException, ParseException {		
		Map<String, String> db_keys = Keys.getMap("db_connection");
		
		_connectionString = String.format("jdbc:%s://%s/%s?user=%s&password=%s&useSSL=False", 
				db_keys.get("driver"),
				db_keys.get("host"),
				db_keys.get("database"),
				db_keys.get("username"),
				db_keys.get("password"));
		
		init(db_keys.get("jdbc_driver"));
	}
	
	public static DatabaseAccess getInstance() throws ClassNotFoundException, FileNotFoundException, IOException, ParseException {
		if (mInstance == null) {	
			mInstance = new DatabaseAccess();
		}
		
		return mInstance;
	}
	
	/**
	 * Initialize db driver
	 * @throws ClassNotFoundException - if the db driver class cannot be located
	 */
	private void init(String dbDriver) throws ClassNotFoundException {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException ex){
			_logger.log(Level.SEVERE, "Error: unable to load db driver class \"" + dbDriver + "\"");
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
	public ResultSet exceute(String sql, List<Object> parameters) throws SQLException {		
		// Using RowSetFactory for CachedRowSet implementation
		RowSetFactory rowSetFactory = RowSetProvider.newFactory();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CachedRowSet crs = rowSetFactory.createCachedRowSet();
		
		try {
			openDBConnection();
			stmt = _con.prepareStatement(sql);
			
			if (parameters != null) {
				for (int i = 0; i < parameters.size(); i++) {
					addParameter(stmt, i + 1, parameters.get(i));
				}
			}
			
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
	public int executeUpdate(String sql, List<Object> parameters) throws SQLException, NumberFormatException {
		PreparedStatement stmt = null;
		int manipulatedRowCount = -1;
		
		try {
			openDBConnection();
			stmt = _con.prepareStatement(sql);
			
			if (parameters != null) {
				for (int i = 0; i < parameters.size(); i++) {
					addParameter(stmt, i + 1, parameters.get(i));
				}
			}

			manipulatedRowCount = stmt.executeUpdate(sql);			
		} finally {
			if (stmt != null) {stmt.close();}
			closeDBConnection();
		}

		return manipulatedRowCount;
	}
	
	private void addParameter(PreparedStatement statement, int index, Object parameter) throws NumberFormatException, SQLException {
		if (parameter instanceof Integer) {
			statement.setInt(index, Integer.parseInt(parameter.toString()));
		} else if (parameter instanceof String) {
			statement.setString(index, parameter.toString());
		} else if (parameter instanceof Date) {
			java.sql.Date convertedDate = new java.sql.Date(((Date)parameter).getTime());
			statement.setDate(index, convertedDate);
		}
	}
}
