package edu.uci.ics.vegao1.service.billing.resources;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.CartInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.models.RequestWrapper;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.records.CartRecords;
import edu.uci.ics.vegao1.service.billing.validation.UserValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("Duplicates")
@Path("cart")
public class CartPage {

    @Path("insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCart(String json) {
        ServiceLogger.LOGGER.info("Received request to insert cart" + json);

        RequestWrapper<CartInsertRequestModel> request = RequestWrapper.map(json, CartInsertRequestModel.class);
        if (request.mappedSuccessfully()) {
            CartInsertRequestModel cartInsertRequest = request.getRequestModel();

            ResponseModel emailCheck = UserValidations.validateEmail(cartInsertRequest.getEmail());
            if (emailCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }

            if (cartInsertRequest.getQuantity() < 1) {
                return Response.status(Response.Status.OK).entity(ResponseModel.QUANTITY_INVALID).build();
            }

            return Response.status(Response.Status.OK).entity(CartRecords.insertCart(cartInsertRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCart(String json) {
        ServiceLogger.LOGGER.info("Received request to update cart" + json);

        RequestWrapper<CartInsertRequestModel> request = RequestWrapper.map(json, CartInsertRequestModel.class);
        if (request.mappedSuccessfully()) {
            CartInsertRequestModel cartInsertRequest = request.getRequestModel();

            ResponseModel emailCheck = UserValidations.validateEmail(cartInsertRequest.getEmail());
            if (emailCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }

            if (cartInsertRequest.getQuantity() < 1) {
                return Response.status(Response.Status.OK).entity(ResponseModel.QUANTITY_INVALID).build();
            }

            return Response.status(Response.Status.OK).entity(CartRecords.updateCart(cartInsertRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

}
