package edu.uci.ics.vegao1.service.movies.resources;

import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.*;
import edu.uci.ics.vegao1.service.movies.records.StarRecords;
import edu.uci.ics.vegao1.service.movies.records.UserRecords;
import edu.uci.ics.vegao1.service.movies.validation.UserValidations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("star")
public class StarPage {
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getId(@Context HttpHeaders headers, @PathParam("id") String id) {
        ServiceLogger.LOGGER.info("Received request to retrieve movie with id: " + id);
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");

        if (email == null || email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new SearchResponseModel(-16, "Email not provided in request header.")).build();
        }
        if (sessionID == null || sessionID.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new SearchResponseModel(-17, "SessionID not provided in request header.")).build();
        }


        ResponseModel emailCheck = UserValidations.validateEmail(email);
        if (emailCheck.equals(ResponseModel.VALID_REQUEST)) {
            GenericResponseModel responseModel = UserRecords.verifyPrivilege(email);

            boolean canAccessHidden = UserRecords.isPrivileged(responseModel);
            StarRequestModel requestModel = new StarRequestModel();
            requestModel.setId(id);

            StarResponseModel starResponseModel = StarRecords.searchStars(requestModel, UserRecords.showHidden(email));
            return Response.status(Response.Status.OK).entity(starResponseModel).build();

        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }


    }
}
