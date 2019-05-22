package edu.uci.ics.vegao1.service.api_gateway.threadpool;

import edu.uci.ics.vegao1.service.api_gateway.models.RequestModel;

public class ClientRequest {
    private String email;
    private String sessionID;
    private String transactionID;
    private RequestModel request;
    private String URI;
    private String endpoint;

    public ClientRequest() {

    }

    public String getEmail() {
        return email;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public RequestModel getRequest() {
        return request;
    }

    public String getURI() {
        return URI;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
