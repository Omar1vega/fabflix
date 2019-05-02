package edu.uci.ics.vegao1.service.billing.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseModel {
    EMAIL_INVALID_FORMAT(-11, "Email address has invalid format."),
    EMAIL_INVALID_LENGTH(-10, "Email address has invalid length"),
    JSON_PARSE_EXCEPTION(-3, "JSON Parse Exception."),
    JSON_MAPPING_EXCEPTION(-2, "JSON Mapping Exception."),
    QUANTITY_INVALID(33, "Quantity has invalid value."),
    DUPLICATE_INSERTION(311, " Duplicate insertion"),
    ITEM_DOES_NOT_EXIST(312, "Shopping item does not exist"),
    CREDIT_CARD_INVALID_LENGTH(321, "Credit card ID has invalid length."),
    CREDIT_CARD_INVALID_VALUE(322, "Credit card ID has invalid value."),
    CREDIT_CARD_EXPIRATION_INVALID_VALUE(323, "expiration has invalid value."),
    CREDIT_CARD_DOES_NOT_EXIST(324, "Credit card does not exist."),
    CREDIT_CARD_DUPLICATE_INSERTION(325, "Duplicate insertion."),
    SHOPPING_CART_INSERT_SUCCESSFUL(3100, "Shopping cart item inserted successfully."),
    SHOPPING_CART_UPDATE_SUCCESSFUL(3100, "Shopping cart item updated successfully."),
    SHOPPING_CART_DELETE_SUCCESSFUL(3120, "Shopping cart item deleted successfully."),
    SHOPPING_CART_RETRIEVED_SUCCESSFUL(3130, "Shopping cart retrieved successfully."),
    SHOPPING_CART_CLEAR_SUCCESSFUL(3140, "Shopping cart cleared successfully."),
    CREDIT_CARD_INSERT_SUCCESSFUL(3200, "Credit card inserted successfully."),
    CREDIT_CARD_UPDATE_SUCCESSFUL(3210, "Credit card updated successfully."),
    CREDIT_CARD_DELETE_SUCCESSFUL(3220, "Credit card deleted successfully."),
    CREDIT_CARD_RETRIEVE_SUCCESSFUL(3230, "Credit card retrieved successfully."),

    VALID_REQUEST(420, "Blaze it");


    private int resultCode;
    private String message;


    ResponseModel(@JsonProperty("resultCode") int resultCode, @JsonProperty("message") String message) {
        this.resultCode = resultCode;
        this.message = message;
    }


    @SuppressWarnings("unused") //Used by Jackson
    @JsonProperty("resultCode")
    public int getResultCode() {
        return resultCode;
    }

    @SuppressWarnings("unused") //Used by Jackson
    @JsonProperty("resultCode")
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


    @SuppressWarnings("unused") //Used by Jackson
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused") //Used by Jackson
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }
}
