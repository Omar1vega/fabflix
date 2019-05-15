package edu.uci.ics.vegao1.service.api_gateway.resources;

import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("test")
public class TestPage {
    @Path("hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        ServiceLogger.LOGGER.info("Hello!");
        return Response.status(200).build();
    }
}
