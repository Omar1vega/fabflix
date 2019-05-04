package edu.uci.ics.vegao1.service.billing.models.order;

import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.records.sales.Order;

import java.util.List;

public class OrderResponseModel {
    private int resultCode;
    private String message;
    private List<Order> items;

    private OrderResponseModel(int resultCode, String message, List<Order> items) {
        this.resultCode = resultCode;
        this.message = message;
        this.items = items;
    }

    public OrderResponseModel(ResponseModel responseModel, List<Order> items) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.items = items;
    }

    public static OrderResponseModel fromResponseModel(ResponseModel responseModel) {
        return new OrderResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null);
    }


    @SuppressWarnings("unused") //Used by Jackson
    public int getResultCode() {
        return resultCode;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMessage(String message) {
        this.message = message;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public List<Order> getItems() {
        return items;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setItems(List<Order> items) {
        this.items = items;
    }
}
