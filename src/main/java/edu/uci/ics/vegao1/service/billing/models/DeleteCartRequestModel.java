package edu.uci.ics.vegao1.service.billing.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteCartRequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "movieId", required = true)
    private String movieId;

    @JsonCreator
    public DeleteCartRequestModel(@JsonProperty(value = "email", required = true) String email,
                                  @JsonProperty(value = "movieId", required = true) String movieId) {
        this.email = email;
        this.movieId = movieId;
    }

    public String getEmail() {
        return email;
    }

    public String getMovieId() {
        return movieId;
    }


    @Override
    public String toString() {
        return "DeleteCartRequestModel{" +
                "email='" + email + '\'' +
                ", movieId='" + movieId + '\'' +
                '}';
    }
}
