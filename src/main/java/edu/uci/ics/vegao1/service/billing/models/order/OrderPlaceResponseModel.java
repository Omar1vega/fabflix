package edu.uci.ics.vegao1.service.billing.models.order;

import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

public class OrderPlaceResponseModel {
    private int resultCode;
    private String message;
    private String redirectURL;
    private String token;

    private OrderPlaceResponseModel(int resultCode, String message, String redirectURL, String token) {
        this.resultCode = resultCode;
        this.message = message;
        this.redirectURL = redirectURL;
        this.token = token;
    }

    public OrderPlaceResponseModel(ResponseModel responseModel, String redirectURL, String token) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.redirectURL = redirectURL;
        this.token = token;
    }

    public static OrderPlaceResponseModel fromResponseModel(ResponseModel responseModel) {
        return new OrderPlaceResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null, null);
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
    public String getRedirectURL() {
        return redirectURL;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getToken() {
        return token;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setToken(String token) {
        this.token = token;
    }
}
