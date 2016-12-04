package javaplay.businesslogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

import javaplay.db.DatabaseAccess;

/**
 * This class represents a Statistics entity
 */
public class Statistics {
	/**
	 * Members
	 */
	@JsonProperty("last_login")
	@SerializedName("last_login")
	private Login mLastLogin;
	@JsonProperty("count")
	@SerializedName("count")
	private int mCount;
	
	/**
	 * Constructor 
	 */
	public Statistics() {
		
	}
	
	/**
	 * Constructor
	 * @param lastLogin last login made by a user
	 * @param count Number of logins made by the user
	 */
	public Statistics(Login lastLogin, int count) {
		mLastLogin = lastLogin;
		mCount = count;
	}
	
	/**
	 * Loads statistics of user logins 
	 * @return List of statistics 
	 * @throws Exception 
	 */
	public static List<Statistics> loadStatistics() throws Exception {
		String sql = 
				"SELECT IMEI, MAX(LOGIN_DATE) as LAST_LOGIN, COUNT(*) as COUNT " 
				+ "FROM LOGIN_EVENTS "
				+ "GROUP BY IMEI";
		
		try {
			ResultSet results = DatabaseAccess.getInstance().exceute(sql, null);
			
			List<Statistics> statsResults = new LinkedList<Statistics>();
			
			// Scans results and convert them into statistics instance 
			while (results.next()) {
				Statistics statistics = new Statistics(
						new Login(new Date(results.getDate("LAST_LOGIN").getTime()), results.getString("IMEI")),
						results.getInt("COUNT"));
				
				statsResults.add(statistics);
			}
			
			return statsResults;
			
		} catch (SQLException ex) {
			throw new Exception("Could not load logins from database", ex);
		}
	}
}
