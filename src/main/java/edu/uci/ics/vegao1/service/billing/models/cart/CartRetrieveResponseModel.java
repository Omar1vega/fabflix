package edu.uci.ics.vegao1.service.billing.models.cart;

import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.records.cart.Cart;

import java.util.List;

public class CartRetrieveResponseModel {
    private int resultCode;
    private String message;
    private List<Cart> items;

    private CartRetrieveResponseModel(int resultCode, String message, List<Cart> items) {
        this.resultCode = resultCode;
        this.message = message;
        this.items = items;
    }

    public CartRetrieveResponseModel(ResponseModel responseModel, List<Cart> items) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.items = items;
    }

    public static CartRetrieveResponseModel fromResponseModel(ResponseModel responseModel) {
        return new CartRetrieveResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null);
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
    public List<Cart> getItems() {
        return items;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setItems(List<Cart> items) {
        this.items = items;
    }
}
