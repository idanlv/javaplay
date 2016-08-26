package javaplay.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import javaplay.modules.UserCredentials;

@Path("/user")
public class UserService {
	static Logger _logger = Logger.getLogger(UserService.class.getName());

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSignUp(UserCredentials credentials) {
		try {
			_logger.log(Level.FINE, "Handling new sign up request: {0}", credentials);
			
			// TODO: Check if user's email already exists in database 
			boolean email_exists = true;
			
			if (email_exists) {
				_logger.log(Level.FINE, "User email already exists in database, cancelling signup");
				return Response.status(403).entity("Email is being used by another user").build();
			}
			
			
			// TODO: Insert user into database
			_logger.log(Level.FINE, "Completed signup process");
			
			return Response.status(200).entity("Success").build(); 
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, ex.toString(), ex);
			return Response.status(500).entity("Unexpected error").build();
		}
	}
}
