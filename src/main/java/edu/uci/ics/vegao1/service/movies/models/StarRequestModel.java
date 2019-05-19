package edu.uci.ics.vegao1.service.movies.models;

import javax.ws.rs.QueryParam;

public class StarRequestModel {
    private String id;
    @QueryParam("name")
    private String name;
    @QueryParam("birthYear")
    private Integer birthYear;
    @QueryParam("movieTitle")
    private String movieTitle;
    @QueryParam("offset")
    private Integer offset;
    @QueryParam("limit")
    private Integer limit;
    @QueryParam("sortby")
    private String sortby;
    @QueryParam("orderby")
    private String orderby;

    @SuppressWarnings("unused") //Used by Jackson
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Integer getBirthYear() {
        return birthYear;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getMovieTitle() {
        return movieTitle;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Integer getOffset() {
        return offset;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Integer getLimit() {
        return limit;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getSortby() {
        return sortby;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getOrderby() {
        return orderby;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        return "StarRequestModel{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", movieTitle='" + movieTitle + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", sortby='" + sortby + '\'' +
                ", orderby='" + orderby + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
