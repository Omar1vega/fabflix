package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.QueryParam;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Star {

    //        “Stars”: [{“id”:”nm0000288”, “name”: “Christian Bale”, “birthYear”: 1974}]
    private String name;
    @QueryParam("name")
    private String id;
    @QueryParam("birthYear")
    private Integer birthYear;

    public Star() {
    }

    public Star(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Star(String id, String name, Integer birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setId(String id) {
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

    @SuppressWarnings("unused") //Used by Jackson
    public Integer getBirthYear() {
        return birthYear;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Star{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
