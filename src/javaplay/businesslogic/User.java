package javaplay.businesslogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaplay.db.DBHandler;
import javaplay.modules.UserCredentials;

public class User {
	static Logger _logger = Logger.getLogger(User.class.getName());
	
	/***
	 * Checks credentials and creates a new user in database
	 * @param credentials user's credentials
	 * @return error message
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean signUp(UserCredentials credentials) throws SQLException {
		DBHandler db = new DBHandler(DBHandler.DB_DEFAULT_HOST, DBHandler.DB_DEFAULT_PORT,
				DBHandler.DB_DEFAULT_NAME, DBHandler.DB_DEFAULT_USER, DBHandler.DB_DEFAULT_PASSWORD);
		
		try {
			db.openDBConnection();
			
			// Checks if another user exists with the same email
			String email_query = 
					String.format("SELECT COUNT(*) AS rowCount FROM USERS WHERE email = '%s'", credentials.getEmail());
			
			ResultSet result = db.exceute(email_query);
			result.next();
	
			boolean email_exists = (result.getInt("rowCount") == 1); 
			
			if (email_exists) {
				_logger.log(Level.INFO, "User email already exists in database");
				return false;
			}
			
			// Saves user's credentials in database
			String signup_insert = String.format("INSERT INTO USERS (EMAIL, PASSWORD, USER_NAME) VALUES ('%s', '%s', '%s')", 
					credentials.getEmail(), credentials.getPassword(), credentials.getUsername());
		
			db.executeUpdate(signup_insert);
			db.closeDBConnection();
			
			return true;
		
		} finally {
			db.closeConnection();
		}
	}
}
