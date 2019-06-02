package edu.uci.ics.vegao1.service.api_gateway.resources;

import edu.uci.ics.vegao1.service.api_gateway.GatewayService;
import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.api_gateway.records.ResponseModel;
import edu.uci.ics.vegao1.service.api_gateway.records.ResponseRecords;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("report")
public class ReportPage {

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(@Context HttpHeaders headers, String json) throws SQLException {
        String transactionID = headers.getHeaderString("transactionID");
        ServiceLogger.LOGGER.info("Received request to get report for transaction: " + transactionID);
        ResponseModel report = ResponseRecords.getReport(transactionID);
        if (report != null) {
            ServiceLogger.LOGGER.info("Report found: " + report.getJson());
            return Response.status(report.getStatus()).entity(report.getJson()).build();
        }
        ServiceLogger.LOGGER.info("No Response found");
        return Response.status(Response.Status.NO_CONTENT).header("transactionID", transactionID).header("requestDelay", GatewayService.getGatewayConfigs().getRequestDelay()).build();
    }
}
