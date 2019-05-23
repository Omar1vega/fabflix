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

@Path("idm")
public class IDMEndpoints {
    private String uri = GatewayService.getIdmConfigs().getIdmUri();

    private Response processRequest(Request requestInfo, UriInfo uriInfo, HttpHeaders httpHeaders, String payload) {
        String transactionID = TransactionIDGenerator.generateTransactionID();
        ClientRequest request = new ClientRequest(transactionID, payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).header("transactionID", transactionID).header("requestDelay", GatewayService.getGatewayConfigs().getRequestDelay()).build();
    }

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUserRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUserRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("session")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifySessionRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("privilege")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyUserPrivilegeRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }
}
