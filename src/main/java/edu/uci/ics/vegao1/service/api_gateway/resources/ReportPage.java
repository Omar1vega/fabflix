package edu.uci.ics.vegao1.service.api_gateway.resources;

import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("report")
public class ReportPage {

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(@Context HttpHeaders headers, String json) {
        ServiceLogger.LOGGER.info("Received request to report" + json);
        return Response.status(Response.Status.OK).build();

//        RequestWrapper<CartInsertRequestModel> request = RequestWrapper.map(json, CartInsertRequestModel.class);
//        if (request.mappedSuccessfully()) {
//            CartInsertRequestModel cartInsertRequest = request.getRequestModel();
//
//            ResponseModel emailCheck = UserValidations.validateEmail(cartInsertRequest.getEmail());
//            if (emailCheck != ResponseModel.VALID_REQUEST) {
//                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
//            }
//
//            if (cartInsertRequest.getQuantity() < 1) {
//                return Response.status(Response.Status.OK).entity(ResponseModel.QUANTITY_INVALID).build();
//            }
//
//            return Response.status(Response.Status.OK).entity(CartRecords.insertCart(cartInsertRequest)).build();
//        } else {
//            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
//            return request.getResponse();
//        }
    }

}
