package javaplay.businesslogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.simple.parser.ParseException;

import com.google.gson.annotations.SerializedName;

import javaplay.db.DatabaseAccess;

/**
 * This class represents a Login entity
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Login {
	/**
	 * Static
	 */
	private static Logger _logger = Logger.getLogger(Login.class.getName());
	
	/**
	 * Members
	 */
    @JsonProperty("login")
    @SerializedName("login")
	private Date mLogin;
    
    @JsonProperty("IMEI")
    @SerializedName("IMEI")
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
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void Save() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException, ParseException { 
		String sql = "INSERT INTO LOGIN_EVENTS (LOGIN_DATE, IMEI) VALUES (?, ?)";

		LinkedList<Object> parameters = new LinkedList<Object>();
		parameters.addLast(mLogin);
		parameters.addLast(mIMEI);
		
		DatabaseAccess.getInstance().executeUpdate(sql, parameters);
	}
	
	/**
	 * Load logins
	 * @param count number of logins to load
	 * @return Login list
	 * @throws Exception
	 */
	public static List<Login> loadLogins(int count) throws Exception {
		String sql = String.format("SELECT LOGIN_DATE, IMEI FROM LOGIN_EVENTS ORDER BY LOGIN_DATE DESC LIMIT %d", count);
		
		try {
			ResultSet results = DatabaseAccess.getInstance().exceute(sql, null);
			
			List<Login> loginResults = new LinkedList<Login>();
			
			// Scan all results and creates login instances 
			while (results.next()) {
				Login login = new Login(
						new Date(results.getDate("LOGIN_DATE").getTime()), 
						results.getString("IMEI"));
				
				loginResults.add(login);
			}
			
			return loginResults;
		} catch (SQLException ex) {
			throw new Exception("Could not load logins from database", ex);
		}
	}
}
