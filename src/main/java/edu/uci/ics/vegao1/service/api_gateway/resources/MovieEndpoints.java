package edu.uci.ics.vegao1.service.api_gateway.resources;

import edu.uci.ics.vegao1.service.api_gateway.GatewayService;
import edu.uci.ics.vegao1.service.api_gateway.threadpool.ClientRequest;
import edu.uci.ics.vegao1.service.api_gateway.util.TransactionIDGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

@Path("movies")
public class MovieEndpoints {
    private String uri = GatewayService.getMovieConfigs().getMoviesUri();

    private Response processRequest(Request requestInfo, UriInfo uriInfo, HttpHeaders httpHeaders, String payload) {
        String transactionID = TransactionIDGenerator.generateTransactionID();
        ClientRequest request = new ClientRequest(transactionID, payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).header("transactionID", transactionID).header("requestDelay", GatewayService.getGatewayConfigs().getRequestDelay()).build();
    }

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("get/{movieid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("delete/{movieid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("genre")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenresRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        ClientRequest request = new ClientRequest(TransactionIDGenerator.generateTransactionID(), payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("genre/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGenreRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("genre/{movieid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenresForMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("star/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response starSearchRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("star/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStarRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("star/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStarRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("star/starsin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStarToMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }

    @Path("rating")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRatingRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        return processRequest(requestInfo, uriInfo, httpHeaders, payload);
    }
}
