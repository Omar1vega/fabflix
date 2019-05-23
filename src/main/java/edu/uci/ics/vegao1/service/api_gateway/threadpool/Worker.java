package edu.uci.ics.vegao1.service.api_gateway.threadpool;

import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.api_gateway.util.Db;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class Worker extends Thread {
    private static final String INSERT_RESPONSE_STATEMENT = "" +
            "INSERT INTO responses (transactionid, email, sessionid, respoonse, httpstatus)\n" +
            "VALUES (?, ?, ?, ?, ?);";
    private int id;
    private ThreadPool threadPool;

    private Worker(int id, ThreadPool threadPool) {
        this.id = id;
        this.threadPool = threadPool;
    }

    static Worker CreateWorker(int id, ThreadPool threadPool) {
        return new Worker(id, threadPool);
    }

    private void process(ClientRequest request) throws SQLException {
        if (request == null) {
            return;
        }
        ServiceLogger.LOGGER.info("Processing request: " + request);
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        ServiceLogger.LOGGER.info("Building URI...");
        String idmUri = request.getURI();

        ServiceLogger.LOGGER.info("Setting path to endpoint...");
        String idmEndpointPath = request.getEndpoint();

        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(idmUri).path(idmEndpointPath);

        ServiceLogger.LOGGER.info("Starting invocation builder...");

        MultivaluedMap<String, String> queryParameters = request.getQueryParameters();
        for (String parameter : queryParameters.keySet()) {
            ServiceLogger.LOGGER.info("Setting query parameter: " + parameter + " = " + queryParameters.getFirst(parameter));
            webTarget = webTarget.queryParam(parameter, queryParameters.getFirst(parameter));
        }

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ServiceLogger.LOGGER.info("Setting payload of the request");

        Entity entity = Entity.entity(request.getPayload(), request.getMediaType());
        Invocation.Builder invocationBuilder2 = invocationBuilder.headers(request.getRequestHeaders());

        ServiceLogger.LOGGER.info("uri: " + webTarget.getUri());


        Invocation invocation = invocationBuilder2.build(request.getMethod(), entity);
        ServiceLogger.LOGGER.info("");
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocation.invoke();

        ServiceLogger.LOGGER.info("Sent!");

        String responseJson = response.readEntity(String.class);
        ServiceLogger.LOGGER.info("response: " + responseJson);
        String transactionID = request.getTransactionID();
        String email = request.getQueryParameters().getFirst("email");
        String sessionId = request.getQueryParameters().getFirst("sessionID");
        int status = response.getStatus();

        Db.executeStatement(INSERT_RESPONSE_STATEMENT, transactionID, email, sessionId, responseJson, status);
    }

    @Override
    public void run() {
        while (true) {
            try {
                process(threadPool.remove());
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
