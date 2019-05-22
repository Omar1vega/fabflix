package edu.uci.ics.vegao1.service.api_gateway.threadpool;

import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.api_gateway.util.Db;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class Worker extends Thread {
    private static final String INSERT_RESPONSE_STATMENT = "";
    int id;
    ThreadPool threadPool;

    private Worker(int id, ThreadPool threadPool) {
        this.id = id;
        this.threadPool = threadPool;
    }

    public static Worker CreateWorker(int id, ThreadPool threadPool) {
        return new Worker(id, threadPool);
    }

    public void process(ClientRequest request) throws SQLException {
        if (request == null) {
            return;
        }

        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        ServiceLogger.LOGGER.info("Building URI...");
        String idmUri = request.getURI();

        ServiceLogger.LOGGER.info("Setting path to endpoint...");
        String idmEndpointPath = request.getEndpoint();

        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(idmUri).path(idmEndpointPath);

        ServiceLogger.LOGGER.info("Starting invocation builder...");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ServiceLogger.LOGGER.info("Setting payload of the request");
        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocationBuilder.post(Entity.entity(request.getRequest(), MediaType.APPLICATION_JSON));
        ServiceLogger.LOGGER.info("Sent!");

        String responseJson = response.getEntity().toString();

        Db.executeStatement(INSERT_RESPONSE_STATMENT, "");
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
