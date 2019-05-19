package edu.uci.ics.vegao1.service.movies.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.movies.records.StarInMovie;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StarResponseModel {
    private int resultCode;
    private String message;
    private List<StarInMovie> stars;

    public StarResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public StarResponseModel(int resultCode, String message, List<StarInMovie> stars) {
        this.resultCode = resultCode;
        this.message = message;
        this.stars = stars;
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
    public List<StarInMovie> getStars() {
        return stars;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setStars(List<StarInMovie> stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "SearchResponseModel{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", stars=" + stars +
                '}';
    }
}
