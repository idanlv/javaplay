package javaplay.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import javaplay.businesslogic.Login;
import javaplay.businesslogic.Statistics;
import javaplay.utils.APIResponse;

@Path("/login")
public class LoginResource {
	@POST
    @Path("/audit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAudit(Login login) {
		APIResponse response = null; 
		try {
	    	login.Save();
	   	    	
	    	response = new APIResponse(APIResponse.SUCCESS, "Login was inserted to database", null);
		} catch (Exception ex) {
			response = new APIResponse(APIResponse.INTERNAL_SERVER_ERROR, "Login wasn't inserted to database", null);
		}
		
		return Response.status(response.getStatusCode()).entity(response).build();
    }
    
    @GET
    @Path("/log/{count}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLog(@PathParam("count") int count) {
    	APIResponse response = null;
    	
    	try {
    		if (count < 1) {
        		response = new APIResponse(APIResponse.BAD_REQUEST, "count parameter must be at least 1", null);
        	} else {
	        	List<Login> logins = Login.loadLogins(count);
	        	
	        	response = new APIResponse(APIResponse.SUCCESS, "Loaded log successfully", logins);
        	}
        } catch (Exception ex) {
        	response = new APIResponse(APIResponse.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    	
    	return Response.status(response.getStatusCode()).entity(response).build();
    }
    
    @GET
    @Path("/statistics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics() {
    	APIResponse response = null;
    	
    	try {
        	List<Statistics> results = Statistics.loadStatistics();
        	
        	response = new APIResponse(APIResponse.SUCCESS, "Statistics were loaded successfully", results);
        } catch (Exception ex) {
        	response = new APIResponse(APIResponse.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    	
    	return Response.status(response.getStatusCode()).entity(response).build();
    }
}
