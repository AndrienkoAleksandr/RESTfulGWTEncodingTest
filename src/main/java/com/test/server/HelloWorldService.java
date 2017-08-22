package com.test.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/echo")
public class HelloWorldService {

	@GET
	@Path("/")
	public Response getHello() {
		String output = "pong";
		return Response.ok().entity(output).build();
	}

	@GET
	@Path("/{param}")
	public Response getEcho(@PathParam("param") String msg) {
		return Response.ok().entity(msg).build();
	}
}