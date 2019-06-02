package edu.uci.ics.vegao1.service.movies.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.*;
import edu.uci.ics.vegao1.service.movies.records.Star;
import edu.uci.ics.vegao1.service.movies.records.StarRecords;
import edu.uci.ics.vegao1.service.movies.records.UserRecords;
import edu.uci.ics.vegao1.service.movies.validation.UserValidations;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("star")
public class StarsPage {
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@Context HttpHeaders headers, @BeanParam StarRequestModel starRequestModel) {
        ServiceLogger.LOGGER.info("Recieved request to search movies: " + starRequestModel);

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
            StarResponseModel responseModel = StarRecords.searchStars(starRequestModel, UserRecords.showHidden(email));
            ServiceLogger.LOGGER.info("Response: " + responseModel);
            return Response.status(Response.Status.OK).entity(responseModel).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getId(@Context HttpHeaders headers, @PathParam("id") String id) {
        ServiceLogger.LOGGER.info("Received request to retrieve star with id: " + id);
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
            StarRequestModel requestModel = new StarRequestModel();
            requestModel.setId(id);

            StarResponseModel starResponseModel = StarRecords.searchStars(requestModel, UserRecords.showHidden(email));
            return Response.status(Response.Status.OK).entity(starResponseModel).build();

        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGenre(@Context HttpHeaders headers, String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to add genre");
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
                Star star;
                try {
                    star = new ObjectMapper().readValue(json, Star.class);
                    return Response.status(Response.Status.OK).entity(StarRecords.addStar(star)).build();

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
