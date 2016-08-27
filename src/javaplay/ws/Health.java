package javaplay.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class Health {

	@GET
	public Response getIsAlive() {
		return Response.status(200).entity("Java Play is Alive!").build();
	}
}
