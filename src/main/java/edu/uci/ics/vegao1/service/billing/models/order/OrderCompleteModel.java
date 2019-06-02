package edu.uci.ics.vegao1.service.billing.models.order;

import javax.ws.rs.QueryParam;

public class OrderCompleteModel {
    @QueryParam("paymentId")
    private String paymentId;
    @QueryParam("token")
    private String token;
    @QueryParam("PayerID")
    private String PayerID;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerID() {
        return PayerID;
    }

    public void setPayerID(String payerID) {
        PayerID = payerID;
    }

    @Override
    public String toString() {
        return "OrderCompleteModel{" +
                "paymentId='" + paymentId + '\'' +
                ", token='" + token + '\'' +
                ", PayerID='" + PayerID + '\'' +
                '}';
    }
}
