package edu.uci.ics.vegao1.service.billing.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class ResponseModel {
    private int resultCode;
    private String message;

    @JsonCreator
    public ResponseModel() {
    }

    public ResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseModel that = (ResponseModel) o;
        return getResultCode() == that.getResultCode() &&
                getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResultCode(), getMessage());
    }
}
