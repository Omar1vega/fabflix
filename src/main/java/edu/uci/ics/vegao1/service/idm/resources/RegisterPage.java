package edu.uci.ics.vegao1.service.idm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.RegisterRequestModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("register")
public class RegisterPage {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld(String json) {
        ServiceLogger.LOGGER.info("Received request to register user");
        ServiceLogger.LOGGER.info(json);

        RegisterRequestModel request;
        try {
            request = new ObjectMapper().readValue(json, RegisterRequestModel.class);
            return Response.status(Response.Status.OK).entity("Parse successful").build();


        } catch (IOException e) {
            String message = e.getClass().getCanonicalName() + e.getLocalizedMessage();
            ServiceLogger.LOGGER.info(message);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
