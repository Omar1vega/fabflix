package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {
    private int id;
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
