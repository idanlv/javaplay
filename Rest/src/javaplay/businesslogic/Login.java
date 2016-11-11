package javaplay.businesslogic;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.annotate.JsonProperty;

import javaplay.db.DatabaseAccess;

public class Login {
	/**
	 * Static
	 */
	private static Logger _logger = Logger.getLogger(Login.class.getName());
	
	/**
	 * Members
	 */
    @JsonProperty("login")
	private Date mLogin;
    @JsonProperty("IMEI")
	private String mIMEI;
    
    /**
     * Empty Constructor for serialization usage
     */
    public Login() {
    	
    }
	
	/**
	 * Constructor
	 * @param login The date which the login was made at
	 * @param IMEI The IMEI which the login was made from
	 */
	public Login(Date login, String IMEI) {
		mLogin = login;
		mIMEI = IMEI;
	}
	
	/**
	 * Getter
	 * @return The date which the login was made at
	 */
	public Date getLogin() {
		return mLogin;
	}
	
	/**
	 *  Getter
	 * @return The IMEI which the login was made from
	 */
	public String getIMEI() {
		return mIMEI;
	}
	
	/**
	 * Saves login instance into database
	 * @return True if the operation was successful, otherwise False
	 */
	public boolean Save() {
		String sql = "INSERT INTO LOGIN_EVENTS (LOGIN_DATE, IMEI) VALUES ('2016-09-30', '1')";

		LinkedList<Object> parameters = new LinkedList<Object>();
		/*parameters.add(0, mLogin);
		parameters.add(1, mIMEI);*/
		
		try {
			int rows = DatabaseAccess.getInstance().executeUpdate(sql, parameters);
			
			return rows == 1;
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}
}
