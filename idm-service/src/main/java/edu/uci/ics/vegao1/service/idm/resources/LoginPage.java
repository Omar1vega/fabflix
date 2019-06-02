package edu.uci.ics.vegao1.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.RegisterResponseModel;
import edu.uci.ics.vegao1.service.idm.models.UserRequestModel;
import edu.uci.ics.vegao1.service.idm.models.UserResponseModel;
import edu.uci.ics.vegao1.service.idm.records.session.SessionRecords;
import edu.uci.ics.vegao1.service.idm.records.user.UserRecord;
import edu.uci.ics.vegao1.service.idm.records.user.UserRecords;
import edu.uci.ics.vegao1.service.idm.security.Crypto;
import edu.uci.ics.vegao1.service.idm.security.Session;
import edu.uci.ics.vegao1.service.idm.validation.UserValidations;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static edu.uci.ics.vegao1.service.idm.validation.UserValidations.VALID_REQUEST;

@Path("login")
public class LoginPage {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        ServiceLogger.LOGGER.info("Received request to login user");
        ServiceLogger.LOGGER.info(json);

        UserRequestModel request;
        try {
            request = new ObjectMapper().readValue(json, UserRequestModel.class);
            UserResponseModel response = UserValidations.processRequest(request);
            if (response.equals(VALID_REQUEST)) {

                String email = request.getEmail();
                UserRecord user = UserRecords.getUser(email);
                if (user == null) {
                    return Response.status(Response.Status.OK).entity(new UserResponseModel(14, "User not found.")).build();
                }
                String hashedPassword = Crypto.hashPassword(request.getPassword(), Hex.decodeHex(user.getSalt()));
                if (hashedPassword.equals(user.getPword())) {
                    ServiceLogger.LOGGER.info("Passwords match, creating new session");
                    SessionRecords.revokeSessions(email);
                    SessionRecords.createSession(Session.createSession(email));
                    return Response.status(Response.Status.OK).entity(new UserResponseModel(120, "User logged in successfully.")).build();
                } else {
                    //Passwords do not match
                    ServiceLogger.LOGGER.info("Expected: " + user.getPword());
                    ServiceLogger.LOGGER.info("Got: " + hashedPassword);
                    return Response.status(Response.Status.OK).entity(new UserResponseModel(11, "Passwords do not match.")).build();
                }

            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }

        } catch (JsonMappingException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-2, "JSON Mapping Exception.")).build();
        } catch (JsonParseException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-3, "JSON Parse Exception.")).build();
        } catch (IOException | DecoderException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
