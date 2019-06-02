package edu.uci.ics.vegao1.service.movies.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.uci.ics.vegao1.service.movies.records.Genre;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreResponseModel {
    private int resultCode;
    private String message;
    private List<Genre> genres;

    public GenreResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public GenreResponseModel(int resultCode, String message, List<Genre> genres) {
        this.resultCode = resultCode;
        this.message = message;
        this.genres = genres;
    }

    public GenreResponseModel(ResponseModel responseModel, List<Genre> genres) {
        this.resultCode = responseModel.getResultCode();
        this.message = responseModel.getMessage();
        this.genres = genres;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "GenreResponseModel{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", genres=" + genres +
                '}';
    }
}
