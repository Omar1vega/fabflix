package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.movies.MovieService;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.GenericResponseModel;
import edu.uci.ics.vegao1.service.movies.models.VerifyPrivilegeRequestModel;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class UserRecords {
    private static final int NON_USER_PRIVILEGE_LEVEL = 3;
    private static final int PRIVILIGED = 140;

    public static boolean showHidden(String email) {
        return isPrivileged(verifyPrivilege(email));
    }

    public static GenericResponseModel verifyPrivilege(String user) {
        ServiceLogger.LOGGER.info("Verifying privilege level with IDM...");
        ServiceLogger.LOGGER.info("Building client...");
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        ServiceLogger.LOGGER.info("Building URI...");
        String idmUri = MovieService.getMovieConfigs().getIdmConfigs().getIdmUri();

        ServiceLogger.LOGGER.info("Setting path to endpoint...");
        String idmEndpointPath = MovieService.getMovieConfigs().getIdmConfigs().getPrivilegePath();

        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(idmUri).path(idmEndpointPath);

        ServiceLogger.LOGGER.info("Starting invocation builder...");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ServiceLogger.LOGGER.info("Setting payload of the request");
        VerifyPrivilegeRequestModel model = new VerifyPrivilegeRequestModel(user, NON_USER_PRIVILEGE_LEVEL);

        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocationBuilder.post(Entity.entity(model, MediaType.APPLICATION_JSON));
        ServiceLogger.LOGGER.info("Sent!");

        // Check that status code of the request
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String json = response.readEntity(String.class);
            ServiceLogger.LOGGER.info("json response: " + json);

            try {
                return new ObjectMapper().readValue(json, GenericResponseModel.class);
            } catch (IOException e) {
                ServiceLogger.LOGGER.info("Error reading response from IDM: " + e.getClass() + e.getCause().getLocalizedMessage());
            }
        }
        return null;
    }

    public static boolean isPrivileged(GenericResponseModel responseModel) {
        return responseModel.getResultCode() == PRIVILIGED;
    }


}
