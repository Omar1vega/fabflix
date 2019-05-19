package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StarInMovie {
    private String id;
    private String name;
    private Integer birthYear;
    private List<StarMovie> movies;

    public StarInMovie(String id, String name, Integer birthYear, List<StarMovie> movies) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.movies = movies;
    }

    public static StarInMovie fromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        Integer birthYear = (Integer) resultSet.getObject("birthYear");


        List<StarMovie> movies = new ArrayList<>();
        String moviesString = resultSet.getString("movies");
        for (String movie : moviesString.split(",")) {
            String movieId = movie.split(":")[1];
            String movieName = movie.split(":")[0];
            movies.add(new StarMovie(movieId, movieName));
        }

        return new StarInMovie(id, name, birthYear, movies);
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

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<StarMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<StarMovie> movies) {
        this.movies = movies;
    }
}
