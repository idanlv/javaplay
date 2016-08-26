package javaplay.modules;

public class UserCredentials {
	private String _email;
	private String _password;
	
	public UserCredentials(String email, String password) {
		_email = email;
		_password = password;
	}
	
	public String getEmail() {
		return _email;
	}
	
	public String getPassword() {
		return _password;
	}
	
	
}
