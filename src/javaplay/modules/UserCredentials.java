package javaplay.modules;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserCredentials {
	@JsonProperty("email")
	private String _email;
	
	@JsonProperty("password")
	private String _password;
	
	@JsonProperty("username")
	private String _username;
	
	/**
	 * Empty Constructor for deserializer
	 */
	public UserCredentials() {
	}
	
	/**
	 * Constructor
	 * @param email user's email
	 * @param username username
	 * @param password user's password
	 */
	public UserCredentials(String email, String username, String password) {
		this._email = email;
		this._username = username;
		this._password = password;
	}
	
	public String getEmail() {
		return this._email;
	}
	
	public void setEmail(String email) {
		this._email = email;
	}
	
	public String getPassword() {
		return this._password;
	}
	
	public void setPassword(String password) {
		this._password = password;
	}
	
	public String getUsername() {
		return this._username;
	}
	
	public void setUsername(String username) {
		this._username = username;
	}
}
