package javaplay.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthResource {

	@GET
	public Response getIsAlive() {
        return Response.status(200).entity("Java Play is Alive!").build();
	}
}
