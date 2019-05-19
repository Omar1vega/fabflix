package edu.uci.ics.vegao1.service.movies.records;

public class StarMovie {
    private String id;
    private String name;

    public StarMovie(String movieId, String title) {
        this.id = movieId;
        this.name = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
