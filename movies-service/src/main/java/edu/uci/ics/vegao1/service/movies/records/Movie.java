package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
    private String movieId;
    private String title;
    private String director;
    private int year;
    private float rating;
    private int numVotes;
    private Boolean hidden;

    public Movie() {
    }

    private Movie(String movieId, String title, int year, String director, float rating, int numVotes, Boolean hidden) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.numVotes = numVotes;
        this.hidden = hidden;
    }

    static Movie fromResultSet(ResultSet resultSet) throws SQLException {
        String movieId = resultSet.getString("id");
        String title = resultSet.getString("title");
        int year = resultSet.getInt("year");
        String director = resultSet.getString("director");
        float rating = resultSet.getFloat("rating");
        int numVotes = resultSet.getInt("numVotes");
        Boolean hidden = resultSet.getInt("hidden") == 1;


        return new Movie(movieId, title, year, director, rating, numVotes, hidden);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getMovieId() {
        return movieId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getYear() {
        return year;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setYear(int year) {
        this.year = year;
    }

    @SuppressWarnings("unused") //Used by Jackson

    public float getRating() {
        return rating;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setRating(float rating) {
        this.rating = rating;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getNumVotes() {
        return numVotes;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void hide() {
        this.hidden = null;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", numVotes=" + numVotes +
                ", hidden=" + hidden +
                '}';
    }
}
