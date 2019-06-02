package edu.uci.ics.vegao1.service.billing.models.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.records.sales.Order;
import edu.uci.ics.vegao1.service.billing.records.sales.Transaction;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseModel {
    private int resultCode;
    private String message;
    private List<Order> items;
    private List<Transaction> transactions;

    private OrderResponseModel(int resultCode, String message, List<Order> items, List<Transaction> transactions) {
        this.resultCode = resultCode;
        this.message = message;
        this.items = items;
        this.transactions = transactions;
    }

    public OrderResponseModel(ResponseModel responseModel, List<Order> items, List<Transaction> transactions) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.items = items;
        this.transactions = transactions;
    }

    public static OrderResponseModel fromResponseModel(ResponseModel responseModel) {
        return new OrderResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null, null);
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

    @SuppressWarnings("unused") //Used by Jackson
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
