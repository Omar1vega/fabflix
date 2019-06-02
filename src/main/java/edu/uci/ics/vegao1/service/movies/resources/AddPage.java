package edu.uci.ics.vegao1.service.movies.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.GenericResponseModel;
import edu.uci.ics.vegao1.service.movies.models.ResponseModel;
import edu.uci.ics.vegao1.service.movies.models.SearchResponseModel;
import edu.uci.ics.vegao1.service.movies.records.FullMovie;
import edu.uci.ics.vegao1.service.movies.records.MovieRecords;
import edu.uci.ics.vegao1.service.movies.records.UserRecords;
import edu.uci.ics.vegao1.service.movies.validation.UserValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("add")
public class AddPage {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovie(@Context HttpHeaders headers, String json) {
        ServiceLogger.LOGGER.info("Received request to add movie");
        ServiceLogger.LOGGER.info(json);

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
                FullMovie movie;
                try {
                    movie = new ObjectMapper().readValue(json, FullMovie.class);

                    MovieRecords.addMovie(movie);
                    return Response.status(Response.Status.OK).entity(ResponseModel.MOVIE_SUCCESSFULLY_ADDED).build();

                } catch (JsonMappingException e) {
                    ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
                    return Response.status(Response.Status.BAD_REQUEST).entity(ResponseModel.JSON_MAPPING_EXCEPTION).build();
                } catch (JsonParseException e) {
                    ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
                    return Response.status(Response.Status.BAD_REQUEST).entity(ResponseModel.JSON_PARSE_EXCEPTION).build();
                } catch (IOException e) {
                    ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }

            } else {
                return Response.status(Response.Status.OK).entity(genericResponseModel).build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }
    }
}
