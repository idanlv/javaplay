package javaplay.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javaplay.businesslogic.Login;

@Path("/login")
public class LoginResource {
	
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int SUCCESS = 200;
    private static final int BAD_REQUEST = 400;

	@POST
    @Path("/audit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAudit(Login login) {
    	boolean result = login.Save();
   
    	if (result) {
    		return Response.status(SUCCESS).entity("Success").build();	
    	} else {
    		return Response.status(INTERNAL_SERVER_ERROR).entity("Failed").build();
    	}
    }
    
    @GET
    @Path("/show")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getShowLogins(@PathParam("count") int count) { 
    	try {
        	
        } catch (Exception ex) {
        	return Response.status(504).entity("").build();
        }
    	
        return Response.status(200).entity("").build();
    }
}
