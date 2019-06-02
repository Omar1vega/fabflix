package edu.uci.ics.vegao1.service.billing.models.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartDeleteRequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "movieId", required = true)
    private String movieId;

    @JsonCreator
    public CartDeleteRequestModel(@JsonProperty(value = "email", required = true) String email,
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
        return "CartDeleteRequestModel{" +
                "email='" + email + '\'' +
                ", movieId='" + movieId + '\'' +
                '}';
    }
}
