package edu.uci.ics.vegao1.service.movies.models;

import javax.ws.rs.QueryParam;

public class SearchRequestModel {
    private String id;
    @QueryParam("title")
    private String title;
    @QueryParam("genre")
    private String genre;
    @QueryParam("hidden")
    private boolean hidden;
    @QueryParam("offset")
    private Integer offset;
    @QueryParam("limit")
    private Integer limit;
    @QueryParam("direction")
    private String direction;
    @QueryParam("orderby")
    private String orderby;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        return "SearchRequestModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", hidden=" + hidden +
                ", offset=" + offset +
                ", limit=" + limit +
                ", direction='" + direction + '\'' +
                ", orderby='" + orderby + '\'' +
                '}';
    }
}
