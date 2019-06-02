package edu.uci.ics.vegao1.service.movies.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.movies.records.Movie;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseModel {
    private int resultCode;
    private String message;
    private List<Movie> movies;

    public SearchResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public SearchResponseModel(int resultCode, String message, List<Movie> movies) {
        this.resultCode = resultCode;
        this.message = message;
        this.movies = movies;
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
    public List<Movie> getMovies() {
        return movies;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "SearchResponseModel{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", movies=" + movies +
                '}';
    }
}
