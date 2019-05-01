package edu.uci.ics.vegao1.service.billing.models;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;

import javax.ws.rs.core.Response;
import java.io.IOException;

public class RequestWrapper<T> {
    private T requestModel;
    private Response response;

    private RequestWrapper(T requestModel) {
        this.requestModel = requestModel;
    }

    private RequestWrapper(Response response) {
        this.response = response;
    }

    public static <V> RequestWrapper<V> map(String content, Class<V> valueType) {
        ServiceLogger.LOGGER.info("Attempting to map " + content + " to " + valueType);
        try {
            V request = new ObjectMapper().readValue(content, valueType);
            ServiceLogger.LOGGER.info("Map successful");
            return new RequestWrapper<>(request);
        } catch (JsonMappingException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return new RequestWrapper<>(Response.status(Response.Status.BAD_REQUEST).entity(new ResponseModel(-2, "JSON Mapping Exception.")).build());
        } catch (JsonParseException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return new RequestWrapper<>(Response.status(Response.Status.BAD_REQUEST).entity(new ResponseModel(-3, "JSON Parse Exception.")).build());
        } catch (IOException e) {
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return new RequestWrapper<>(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    public T getRequestModel() {
        return requestModel;
    }

    public Response getResponse() {
        return response;
    }

    public boolean mappedSuccessfully() {
        return requestModel != null;
    }

    @Override
    public String toString() {
        return "RequestWrapper{" +
                "requestModel=" + requestModel +
                ", response=" + response +
                '}';
    }
}
