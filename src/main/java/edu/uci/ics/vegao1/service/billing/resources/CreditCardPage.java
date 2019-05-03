package edu.uci.ics.vegao1.service.billing.resources;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.RequestWrapper;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.creditcard.CreditCardInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.records.creditcard.CreditCardRecords;
import edu.uci.ics.vegao1.service.billing.validation.CreditCardValidations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;

@SuppressWarnings("Duplicates")
@Path("creditcard")
public class CreditCardPage {

    @Path("insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCreditCard(String json) throws ParseException, SQLException {
        ServiceLogger.LOGGER.info("Received request to insert creditcard" + json);

        RequestWrapper<CreditCardInsertRequestModel> request = RequestWrapper.map(json, CreditCardInsertRequestModel.class);
        if (request.mappedSuccessfully()) {
            CreditCardInsertRequestModel creditCardInsertRequest = request.getRequestModel();

            ResponseModel creditCheck = CreditCardValidations.validateCreditCard(creditCardInsertRequest.getId(), creditCardInsertRequest.getExpiration());
            if (creditCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(creditCheck).build();
            }

            return Response.status(Response.Status.OK).entity(CreditCardRecords.insertCard(creditCardInsertRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }

    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCreditCard(String json) throws ParseException, SQLException {
        ServiceLogger.LOGGER.info("Received request to update creditcard" + json);

        RequestWrapper<CreditCardInsertRequestModel> request = RequestWrapper.map(json, CreditCardInsertRequestModel.class);
        if (request.mappedSuccessfully()) {
            CreditCardInsertRequestModel creditCardInsertRequest = request.getRequestModel();

            ResponseModel creditCheck = CreditCardValidations.validateCreditCard(creditCardInsertRequest.getId(), creditCardInsertRequest.getExpiration());
            if (creditCheck != ResponseModel.VALID_REQUEST) {
                return Response.status(Response.Status.BAD_REQUEST).entity(creditCheck).build();
            }

            return Response.status(Response.Status.OK).entity(CreditCardRecords.updateCardInfo(creditCardInsertRequest)).build();
        } else {
            ServiceLogger.LOGGER.info("request mapping was unsuccessful");
            return request.getResponse();
        }
    }
}
