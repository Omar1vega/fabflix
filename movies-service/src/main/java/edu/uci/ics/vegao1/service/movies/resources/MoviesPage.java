package edu.uci.ics.vegao1.service.movies.resources;

import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.*;
import edu.uci.ics.vegao1.service.movies.records.MovieRecords;
import edu.uci.ics.vegao1.service.movies.records.UserRecords;
import edu.uci.ics.vegao1.service.movies.validation.UserValidations;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/")
public class MoviesPage {
    private static final int MOVIES_FOUMD = 210;

    @Path("/get/{id}")
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
            SearchRequestModel requestModel = new SearchRequestModel();
            requestModel.setId(id);
            SearchFullResponseModel searchResponseModel = MovieRecords.searchFullMovies(requestModel, true);
            boolean movieIsHidden = searchResponseModel.getResultCode() == MOVIES_FOUMD && searchResponseModel.getMovies().get(0).getHidden();
            ServiceLogger.LOGGER.info("Response: " + searchResponseModel);

            if (movieIsHidden && !canAccessHidden) {
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            } else {
                return Response.status(Response.Status.OK).entity(searchResponseModel).build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteId(@Context HttpHeaders headers, @PathParam("id") String id) throws SQLException {
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
            GenericResponseModel genericResponseModel = UserRecords.verifyPrivilege(email);
            boolean userIsPrivileged = UserRecords.isPrivileged(genericResponseModel);
            if (userIsPrivileged) {
                return Response.status(Response.Status.BAD_REQUEST).entity(MovieRecords.hideMovie(id)).build();
            } else {
                return Response.status(Response.Status.OK).entity(genericResponseModel).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }
    }
}
