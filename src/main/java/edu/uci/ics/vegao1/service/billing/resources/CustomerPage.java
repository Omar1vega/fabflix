package edu.uci.ics.vegao1.service.billing.resources;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.RequestWrapper;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.creditcard.CreditCardDeleteRequestModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerRetrieveRequestModel;
import edu.uci.ics.vegao1.service.billing.records.creditcard.CreditCardRecords;
import edu.uci.ics.vegao1.service.billing.records.customer.CustomerRecords;
import edu.uci.ics.vegao1.service.billing.validation.CreditCardValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@SuppressWarnings("Duplicates")
@Path("customer")
public class CustomerPage {

    @Path("insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCustomer(String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to insert creditcard" + json);

        RequestWrapper<CustomerModel> request = RequestWrapper.map(json, CustomerModel.class);
        if (request.mappedSuccessfully()) {
            CustomerModel customerModel = request.getRequestModel();

            ResponseModel creditCheck = CreditCardValidations.validateId(customerModel.getCcId());
            if (creditCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(creditCheck).build();
            }

            boolean creditCardExists = CreditCardRecords.retrieveCreditCard(new CreditCardDeleteRequestModel(customerModel.getCcId())).getResultCode() == ResponseModel.CREDIT_CARD_RETRIEVE_SUCCESSFUL.getResultCode();
            if (!creditCardExists) {
                return Response.status(Response.Status.OK).entity(ResponseModel.CREDIT_CARD_ID_NOT_FOUND).build();
            }

            return Response.status(Response.Status.OK).entity(CustomerRecords.insertCustomer(customerModel)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to update creditcard" + json);

        RequestWrapper<CustomerModel> request = RequestWrapper.map(json, CustomerModel.class);
        if (request.mappedSuccessfully()) {
            CustomerModel customer = request.getRequestModel();

            ResponseModel creditCheck = CreditCardValidations.validateId(customer.getCcId());
            if (creditCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(creditCheck).build();
            }
            boolean creditCardExists = CreditCardRecords.retrieveCreditCard(new CreditCardDeleteRequestModel(customer.getCcId())).getResultCode() == ResponseModel.CREDIT_CARD_RETRIEVE_SUCCESSFUL.getResultCode();
            if (!creditCardExists) {
                return Response.status(Response.Status.OK).entity(ResponseModel.CREDIT_CARD_ID_NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(CustomerRecords.updateCustomer(customer)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomer(String json) throws SQLException {
        ServiceLogger.LOGGER.info("Received request to retrieve creditcard" + json);

        RequestWrapper<CustomerRetrieveRequestModel> request = RequestWrapper.map(json, CustomerRetrieveRequestModel.class);
        if (request.mappedSuccessfully()) {
            CustomerRetrieveRequestModel creditCardRetrieveRequest = request.getRequestModel();

            return Response.status(Response.Status.OK).entity(CustomerRecords.retrieveCustomer(creditCardRetrieveRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }
}
