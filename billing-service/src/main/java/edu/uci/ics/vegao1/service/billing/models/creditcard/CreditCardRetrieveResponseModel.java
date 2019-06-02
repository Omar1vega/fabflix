package edu.uci.ics.vegao1.service.billing.models.creditcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.records.creditcard.CreditCard;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardRetrieveResponseModel {
    private int resultCode;
    private String message;
    private CreditCard creditcard;

    private CreditCardRetrieveResponseModel(int resultCode, String message, CreditCard creditcard) {
        this.resultCode = resultCode;
        this.message = message;
        this.creditcard = creditcard;
    }

    public CreditCardRetrieveResponseModel(ResponseModel responseModel, CreditCard creditcard) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.creditcard = creditcard;
    }

    public static CreditCardRetrieveResponseModel fromResponseModel(ResponseModel responseModel) {
        return new CreditCardRetrieveResponseModel(responseModel.getResultCode(), responseModel.getMessage(), null);
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
    public CreditCard getCreditcard() {
        return creditcard;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;
    }
}
