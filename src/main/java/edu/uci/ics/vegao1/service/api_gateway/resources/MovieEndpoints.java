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

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        String transactionID = TransactionIDGenerator.generateTransactionID();
        ClientRequest request = new ClientRequest(transactionID, payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).header("transactionID", transactionID).header("requestDelay", GatewayService.getGatewayConfigs().getRequestDelay()).build();
    }

    @Path("get/{movieid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieRequest(@Context Request requestInfo, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String payload) {
        ClientRequest request = new ClientRequest(TransactionIDGenerator.generateTransactionID(), payload, uri, uriInfo.getPathSegments(), requestInfo.getMethod(), httpHeaders.getAcceptableMediaTypes(), httpHeaders.getRequestHeaders(), uriInfo.getQueryParameters());
        GatewayService.getThreadPool().getQueue().enqueue(request);

        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovieRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("delete/{movieid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovieRequest() {
        return Response.status(Status.NO_CONTENT).build();
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
    public Response addGenreRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("genre/{movieid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenresForMovieRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("star/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response starSearchRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("star/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStarRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("star/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStarRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("star/starsin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStarToMovieRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }

    @Path("rating")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRatingRequest() {
        return Response.status(Status.NO_CONTENT).build();
    }
}
