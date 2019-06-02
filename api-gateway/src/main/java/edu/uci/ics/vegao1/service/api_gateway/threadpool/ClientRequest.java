package edu.uci.ics.vegao1.service.api_gateway.threadpool;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import java.util.ArrayList;
import java.util.List;

public class ClientRequest {
    private String transactionID;
    private String payload;
    private String URI;
    private String endpoint;
    private String method;
    private List<MediaType> mediaTypes;
    private MultivaluedMap<String, String> requestHeaders;
    private MultivaluedMap<String, String> queryParameters;

    public ClientRequest(String transactionID, String payload, String URI, List<PathSegment> pathSegments, String method, List<MediaType> mediaTypes, MultivaluedMap<String, String> requestHeaders, MultivaluedMap<String, String> queryParameters) {
        this.transactionID = transactionID;
        this.payload = payload.trim().isEmpty() ? null : payload;
        this.URI = URI;
        List<PathSegment> segments = new ArrayList<>(pathSegments);
        segments.remove(0);
        StringBuilder endpointBuilder = new StringBuilder();
        for (PathSegment pathSegment : segments) {
            endpointBuilder.append(pathSegment);
            endpointBuilder.append("/");
        }
        this.endpoint = endpointBuilder.toString();
        this.method = method;
        this.mediaTypes = mediaTypes;
        this.requestHeaders = requestHeaders;
        this.queryParameters = queryParameters;
    }


    String getPayload() {
        return payload;
    }


    String getURI() {
        return URI;
    }

    String getEndpoint() {
        return endpoint;
    }

    MediaType getMediaType() {
        if (mediaTypes == null || mediaTypes.isEmpty()) {
            return MediaType.APPLICATION_JSON_TYPE;
        }
        return mediaTypes.get(0);
    }


    MultivaluedMap<String, Object> getRequestHeaders() {
        MultivaluedHashMap<String, Object> map = new MultivaluedHashMap<>();
        for (String key : requestHeaders.keySet()) {
            map.put(key, new ArrayList<>(requestHeaders.get(key)));
        }
        return map;
    }

    String getMethod() {
        return method;
    }

    String getTransactionID() {
        return transactionID;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "transactionID='" + transactionID + '\'' +
                ", payload='" + payload + '\'' +
                ", URI='" + URI + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", method='" + method + '\'' +
                ", mediaTypes=" + mediaTypes +
                ", requestHeaders=" + requestHeaders +
                ", queryParameters=" + queryParameters +
                '}';
    }

    MultivaluedMap<String, String> getQueryParameters() {
        return queryParameters;
    }
}
