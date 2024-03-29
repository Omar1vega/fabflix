package edu.uci.ics.vegao1.service.movies.resources;

import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.ResponseModel;
import edu.uci.ics.vegao1.service.movies.models.SearchRequestModel;
import edu.uci.ics.vegao1.service.movies.models.SearchResponseModel;
import edu.uci.ics.vegao1.service.movies.records.MovieRecords;
import edu.uci.ics.vegao1.service.movies.records.UserRecords;
import edu.uci.ics.vegao1.service.movies.validation.UserValidations;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("search")
public class SearchPage {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@Context HttpHeaders headers, @BeanParam SearchRequestModel searchRequestModel) {
        ServiceLogger.LOGGER.info("Recieved request to search movies: " + searchRequestModel);

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
            SearchResponseModel responseModel = MovieRecords.searchMovies(searchRequestModel, UserRecords.showHidden(email));
            ServiceLogger.LOGGER.info("Response: " + responseModel);
            return Response.status(Response.Status.OK).entity(responseModel).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
        }

    }
}
