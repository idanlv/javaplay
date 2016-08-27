package javaplay.modules;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserCredentials {
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String username;
	
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
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
