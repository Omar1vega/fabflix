package edu.uci.ics.vegao1.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.RegisterRequestModel;
import edu.uci.ics.vegao1.service.idm.models.RegisterResponseModel;
import edu.uci.ics.vegao1.service.idm.records.user.UserRecords;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.regex.Pattern;

@Path("register")
public class RegisterPage {

    private static final Pattern emailPattern = Pattern.compile("(^.+)@(.+)\\.(.+)");

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
            return processRequest(request);
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

    private Response processRequest(RegisterRequestModel requestModel) {
        String email = requestModel.getEmail();
        char[] password = requestModel.getPassword();

        if (password == null || password.length == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-12, "Password has invalid length (cannot be empty/null).")).build();
        }

        if (!emailPattern.matcher(email).matches()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-11, "Email address has invalid format.")).build();
        }

        if (email.length() > 50) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(-10, "Email address has invalid length.")).build();
        }

        if (password.length < 7 || password.length > 16) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(12, "Password has invalid length (cannot be empty/null).")).build();
        }

        if (!validPassword(password)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(13, "Password does not meet character requirements")).build();
        }

        if (UserRecords.emailInUse(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new RegisterResponseModel(16, "Email already in use.")).build();
        }

        UserRecords.createUser(requestModel);
        return Response.status(Response.Status.OK).entity(new RegisterResponseModel(110, "User registered successfully.")).build();
    }

    private boolean validPassword(char[] password) {
        boolean upper = false, lower = false, numeric = false, symbol = false;

        for (char c : password) {
            if (Character.isUpperCase(c)) {
                upper = true;
            }
            if (Character.isLowerCase(c)) {
                lower = true;
            }
            if (Character.isDigit(c)) {
                numeric = true;
            }
            if (!(Character.isAlphabetic(c) && Character.isDigit(c))) {
                symbol = true;
            }
        }

        return upper && lower && numeric && symbol;

    }

}
