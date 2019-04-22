package edu.uci.ics.vegao1.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.RegisterResponseModel;
import edu.uci.ics.vegao1.service.idm.models.SessionRequestModel;
import edu.uci.ics.vegao1.service.idm.models.SessionResponseModel;
import edu.uci.ics.vegao1.service.idm.models.UserResponseModel;
import edu.uci.ics.vegao1.service.idm.records.session.SessionRecords;
import edu.uci.ics.vegao1.service.idm.records.user.UserRecords;
import edu.uci.ics.vegao1.service.idm.security.Session;
import edu.uci.ics.vegao1.service.idm.validation.UserValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static edu.uci.ics.vegao1.service.idm.validation.UserValidations.VALID_REQUEST;

@Path("session")
public class SessionPage {
    private static final int TOKEN_LENGTH = 128;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response session(String json) {
        ServiceLogger.LOGGER.info("Received request to verify that a provided username and token is valid");
        ServiceLogger.LOGGER.info(json);

        SessionRequestModel request;
        try {
            request = new ObjectMapper().readValue(json, SessionRequestModel.class);
            if (request.getSessionID().trim().length() != TOKEN_LENGTH) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-13, "Token has invalid length.")).build();
            }

            UserResponseModel emailCheck = UserValidations.validateEmail(request.getEmail());
            if (emailCheck.equals(VALID_REQUEST)) {
                if (!UserRecords.emailInUse(request.getEmail())) {
                    return Response.status(Response.Status.OK).entity(new RegisterResponseModel(14, "User not found.")).build();
                }

                int sessionStatus = SessionRecords.getSessionStatus(request.getSessionID());
                switch (sessionStatus) {
                    case Session.ACTIVE:
                        return Response.status(Response.Status.OK).entity(new SessionResponseModel(130, "Session is active.", request.getSessionID())).build();
                    case Session.EXPIRED:
                        return Response.status(Response.Status.OK).entity(new UserResponseModel(131, "Session is expired.")).build();
                    case Session.CLOSED:
                        return Response.status(Response.Status.OK).entity(new UserResponseModel(132, "Session is closed.")).build();
                    case Session.REVOKED:
                        return Response.status(Response.Status.OK).entity(new UserResponseModel(133, "Session is revoked.")).build();
                    default:
                        return Response.status(Response.Status.OK).entity(new UserResponseModel(134, "Session not found.")).build();
                }

            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }


        } catch (JsonMappingException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-3, "JSON Parse Exception.")).build();
        } catch (JsonParseException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-2, "JSON Mapping Exception.")).build();
        } catch (IOException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
