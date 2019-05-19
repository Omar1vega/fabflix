package edu.uci.ics.vegao1.service.movies.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseModel {
    JSON_PARSE_EXCEPTION(-3, "JSON Parse Exception."),
    JSON_MAPPING_EXCEPTION(-2, "JSON Mapping Exception."),
    INSUFFIECIENT_PRIVILEGE(141, "User has insufficient privilege."),

    SEARCH_MOVIES_FOUND(210, "Found movies with search parameters."),
    SEARCH_NO_MOVIES_FOUND(211, "No movies found with search parameters."),
    SEARCH_STARS_FOUND(212, "Found stars with search parameters."),
    SEARCH_NO_STARS_FOUND(213, "No stars found with search parameters."),

    MOVIE_SUCCESSFULLY_ADDED(214, "Movie successfully added."),
    MOVIE_COULD_NOT_BE_ADDED(215, "Could not add movie."),
    MOVIE_ALREADY_EXISTS(216, "Movie already exists."),

    GENRE_SUCCESSFULLY_ADDED(217, "Genre successfully added."),
    GENRE_COULD_NOT_BE_ADDED(218, "Genre could not be added."),
    GENRES_SUCCESSFULLY_RETRIEVED(219, "Genres successfully retrieved."),

    STAR_ALREADY_EXISTS(222, "Star already exists."),
    STAR_SUCCESSFULLY_ADDED(220, "Star successfully added."),
    STAR_COULD_NOT_BE_ADDED(221, "Could not add star."),

    STAR_SUCCESSFULLY_ADDED_TO_MOVIE(230, "Star successfully added to movie."),
    STAR_COULD_NOT_BE_ADDED_TO_MOVIE(231, "Could not add star to movie."),
    STAR_ALREADY_EXISTS_IN_MOVIE(232, "Star already exists in movie."),

    MOVIE_SUCCESSFULLY_REMOVED(240, "Movie successfully removed."),
    MOVIE_COULD_NOT_BE_REMOVED(241, "Could not remove movie."),
    MOVIE_ALREADY_REMOVED(242, "Movie has been already removed."),

    RATING_SUCCESSFULLY_UPDATED(250, "Rating successfully updated."),
    RATING_COULD_NOT_BE_UPDATED(251, "Could not update rating."),


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

