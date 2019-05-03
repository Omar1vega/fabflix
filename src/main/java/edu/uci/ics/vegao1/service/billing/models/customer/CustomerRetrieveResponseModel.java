package edu.uci.ics.vegao1.service.billing.models.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRetrieveResponseModel {
    private int resultCode;
    private String message;
    private CustomerModel customer;

    private CustomerRetrieveResponseModel(int resultCode, String message, CustomerModel customer) {
        this.resultCode = resultCode;
        this.message = message;
        this.customer = customer;
    }

    public CustomerRetrieveResponseModel(ResponseModel responseModel, CustomerModel customer) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.customer = customer;
    }

    public static CustomerRetrieveResponseModel fromResponseModel(ResponseModel responseModel) {
        return new CustomerRetrieveResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null);
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
    public CustomerModel getCustomer() {
        return customer;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }
}
