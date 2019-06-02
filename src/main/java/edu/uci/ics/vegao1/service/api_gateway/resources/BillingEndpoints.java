package edu.uci.ics.vegao1.service.api_gateway.resources;

import edu.uci.ics.vegao1.service.api_gateway.GatewayService;
import edu.uci.ics.vegao1.service.api_gateway.threadpool.ClientRequest;
import edu.uci.ics.vegao1.service.api_gateway.util.TransactionIDGenerator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

@Path("billing")
public class BillingEndpoints {
    private String uri = GatewayService.getBillingConfigs().getBillingUri();

    private Response processRequest(Request requestInfo, UriInfo uriInfo, HttpHeaders httpHeaders, String payload) {
        String sessionId = httpHeaders.getHeaderString("sessionID");
        String transactionID = TransactionIDGenerator.generateTransactionID();
        ClientRequest request = new ClientRequest(transactionID, payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).header("transactionID", transactionID).header("sessionID", sessionId).header("requestDelay", GatewayService.getGatewayConfigs().getRequestDelay()).build();
    }

    @Path("cart/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertToCartRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("cart/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("cart/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCartRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("cart/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCartRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("cart/clear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearCartRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("creditcard/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCreditCardRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("creditcard/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCreditCardRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("creditcard/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCardRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("creditcard/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCreditCardRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("customer/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCustomerRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("customer/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("customer/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomerRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("order/place")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrderRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("order/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOrderRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }
}
