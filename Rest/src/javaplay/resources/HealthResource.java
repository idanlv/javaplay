package javaplay.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import javaplay.utils.APIResponse;

/**
 * Health resource 
 */
@Path("/health")
public class HealthResource {

	/**
	 * Checks if service is up
	 * @return response 
	 */
	@GET
	public Response getIsAlive() {
        APIResponse response = new APIResponse(APIResponse.SUCCESS, "JavaPlay Rest is alive!", null);
        
        return Response.status(response.getStatusCode()).entity(response).build();
	}
}
