package edu.uci.ics.vegao1.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.PrivilegeRequestModel;
import edu.uci.ics.vegao1.service.idm.models.RegisterResponseModel;
import edu.uci.ics.vegao1.service.idm.models.UserResponseModel;
import edu.uci.ics.vegao1.service.idm.records.user.UserRecords;
import edu.uci.ics.vegao1.service.idm.validation.UserValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static edu.uci.ics.vegao1.service.idm.records.privilege.PrivilegeRecords.getPrivilegeLevels;
import static edu.uci.ics.vegao1.service.idm.records.privilege.PrivilegeRecords.privilegeIsSufficient;
import static edu.uci.ics.vegao1.service.idm.validation.UserValidations.VALID_REQUEST;

@Path("privilege")
public class PrivilegePage {
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response privilege(String json) {
        ServiceLogger.LOGGER.info("Received request to check privilege");
        ServiceLogger.LOGGER.info(json);

        PrivilegeRequestModel request;
        try {
            request = new ObjectMapper().readValue(json, PrivilegeRequestModel.class);
            UserResponseModel emailCheck = UserValidations.validateEmail(request.getEmail());
            if (emailCheck.equals(VALID_REQUEST)) {
                if (!UserRecords.emailInUse(request.getEmail())) {
                    return Response.status(Response.Status.OK).entity(new RegisterResponseModel(14, "User not found.")).build();
                }

                int privilegeLevel = request.getPlevel();
                if (getPrivilegeLevels().contains(privilegeLevel)) {
                    if (privilegeIsSufficient(request.getEmail(), request.getPlevel())) {
                        return Response.status(Response.Status.OK).entity(new RegisterResponseModel(140, "User has sufficient privilege level.")).build();
                    } else {
                        return Response.status(Response.Status.OK).entity(new RegisterResponseModel(141, "User has insufficient privilege level.")).build();
                    }

                } else {// Privilege level out of valid range.
                    return Response.status(Response.Status.OK).entity(new RegisterResponseModel(-14, "Privilege level out of valid range.")).build();
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
