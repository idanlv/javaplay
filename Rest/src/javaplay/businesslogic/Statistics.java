package javaplay.businesslogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

import javaplay.db.DatabaseAccess;

public class Statistics {
	@JsonProperty("last_login")
	@SerializedName("last_login")
	private Login mLastLogin;
	@JsonProperty("count")
	@SerializedName("count")
	private int mCount;
	
	public Statistics() {
		
	}
	
	public Statistics(Login lastLogin, int count) {
		mLastLogin = lastLogin;
		mCount = count;
	}
	
	public static List<Statistics> loadStatistics() throws Exception {
		String sql = 
				"SELECT IMEI, MAX(LOGIN_DATE) as LAST_LOGIN, COUNT(*) as COUNT " 
				+ "FROM LOGIN_EVENTS "
				+ "GROUP BY IMEI";
		
		try {
			ResultSet results = DatabaseAccess.getInstance().exceute(sql, null);
			
			List<Statistics> statsResults = new LinkedList<Statistics>();
			
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
