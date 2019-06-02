package edu.uci.ics.vegao1.service.billing.resources;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.RequestWrapper;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.cart.CartDeleteRequestModel;
import edu.uci.ics.vegao1.service.billing.models.cart.CartInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.models.cart.CartRetrieveRequestModel;
import edu.uci.ics.vegao1.service.billing.records.cart.CartRecords;
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

    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCart(String json) {
        ServiceLogger.LOGGER.info("Received request to delete cart");
        RequestWrapper<CartDeleteRequestModel> request = RequestWrapper.map(json, CartDeleteRequestModel.class);
        if (request.mappedSuccessfully()) {
            CartDeleteRequestModel cartDeleteRequest = request.getRequestModel();

            ResponseModel emailCheck = UserValidations.validateEmail(cartDeleteRequest.getEmail());
            if (emailCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }
            return Response.status(Response.Status.OK).entity(CartRecords.deleteCart(cartDeleteRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCart(String json) {
        ServiceLogger.LOGGER.info("Received request to retrieve cart");
        RequestWrapper<CartRetrieveRequestModel> request = RequestWrapper.map(json, CartRetrieveRequestModel.class);
        if (request.mappedSuccessfully()) {
            CartRetrieveRequestModel cartDeleteRequest = request.getRequestModel();

            ResponseModel emailCheck = UserValidations.validateEmail(cartDeleteRequest.getEmail());
            if (emailCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }
            return Response.status(Response.Status.OK).entity(CartRecords.retrieveCart(cartDeleteRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("clear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearCart(String json) {
        ServiceLogger.LOGGER.info("Received request to clear cart");
        RequestWrapper<CartRetrieveRequestModel> request = RequestWrapper.map(json, CartRetrieveRequestModel.class);
        if (request.mappedSuccessfully()) {
            CartRetrieveRequestModel cartClearRequest = request.getRequestModel();

            ResponseModel emailCheck = UserValidations.validateEmail(cartClearRequest.getEmail());
            if (emailCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(emailCheck).build();
            }
            return Response.status(Response.Status.OK).entity(CartRecords.clearCart(cartClearRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

}
