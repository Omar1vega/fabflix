package edu.uci.ics.vegao1.service.billing.resources;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.RequestWrapper;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerRetrieveRequestModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderCompleteModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderRequestModel;
import edu.uci.ics.vegao1.service.billing.records.customer.CustomerRecords;
import edu.uci.ics.vegao1.service.billing.records.sales.SalesRecords;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@SuppressWarnings("Duplicates")
@Path("order")
public class OrderPage {

    @Path("place")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(@Context UriInfo uriInfo, String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to place order" + json);

        RequestWrapper<OrderRequestModel> request = RequestWrapper.map(json, OrderRequestModel.class);
        if (request.mappedSuccessfully()) {
            OrderRequestModel orderPlaceRequest = request.getRequestModel();

            boolean customerExists = CustomerRecords.retrieveCustomer(new CustomerRetrieveRequestModel(orderPlaceRequest.getEmail())).getResultCode() == ResponseModel.CUSTOMER_RETRIEVE_SUCCESSFUL.getResultCode();
            if (!customerExists) {
                return Response.status(Response.Status.OK).entity(ResponseModel.CUSTOMER_DOES_NOT_EXIST).build();
            }
            String returnUrl = uriInfo.getBaseUri().resolve("order/complete").toString();
            return Response.status(Response.Status.OK).entity(SalesRecords.placeOrder(orderPlaceRequest, returnUrl)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }


    @Path("retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOrder(String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to retrieve creditcard" + json);

        RequestWrapper<OrderRequestModel> request = RequestWrapper.map(json, OrderRequestModel.class);
        if (request.mappedSuccessfully()) {
            OrderRequestModel orderRetrieveRequest = request.getRequestModel();

            return Response.status(Response.Status.OK).entity(SalesRecords.retrieveOrder(orderRetrieveRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("complete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeOrder(@BeanParam OrderCompleteModel orderCompleteModel) {
        ServiceLogger.LOGGER.info("Received request to complete order" + orderCompleteModel);

        return Response.status(Response.Status.OK).entity(SalesRecords.completeOrder(orderCompleteModel)).build();
    }
}
