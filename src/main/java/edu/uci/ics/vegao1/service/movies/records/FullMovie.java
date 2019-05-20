package edu.uci.ics.vegao1.service.movies.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullMovie {
    private String movieId;
    private String title;
    private int year;
    private String director;
    private String backdrop_path;
    private Integer budget;
    private String overview;
    private String poster_path;
    private Integer revenue;
    private float rating;
    private int numVotes;
    private Boolean hidden;
    private List<Genre> genres;
    private List<Star> stars;

    public FullMovie() {
    }

    private FullMovie(String movieId, String title, int year, String director, String backdrop_path, Integer budget, String overview, String poster_path, Integer revenue, float rating, int numVotes, Boolean hidden, List<Genre> genres, List<Star> stars) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.director = director;
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.overview = overview;
        this.poster_path = poster_path;
        this.revenue = revenue;
        this.rating = rating;
        this.numVotes = numVotes;
        this.hidden = hidden;
        this.genres = genres;
        this.stars = stars;
    }

    static FullMovie fromResultSet(ResultSet resultSet) throws SQLException {
        String movieId = resultSet.getString("id");
        String title = resultSet.getString("title");
        int year = resultSet.getInt("year");
        String director = resultSet.getString("director");
        String backdrop_path = resultSet.getString("backdrop_path");
        Integer budget = (Integer) resultSet.getObject("budget");
        String poster_path = resultSet.getString("poster_path");
        String overview = resultSet.getString("overview");
        Integer revenue = (Integer) resultSet.getObject("revenue");
        float rating = resultSet.getFloat("rating");
        int numVotes = resultSet.getInt("numVotes");
        Boolean hidden = resultSet.getInt("hidden") == 1;

        List<Genre> genres = new ArrayList<>();
        String genreString = resultSet.getString("genres");
        for (String genre : genreString.split(",")) {
            int id = Integer.valueOf(genre.split(":")[1]);
            String name = genre.split(":")[0];
            genres.add(new Genre(id, name));
        }

        List<Star> stars = new ArrayList<>();
        String starString = resultSet.getString("stars");
        for (String star : starString.split(",")) {
            String id = star.split(":")[1];
            String name = star.split(":")[0];
            stars.add(new Star(id, name));
        }

        return new FullMovie(movieId, title, year, director, backdrop_path, budget, overview, poster_path, revenue, rating, numVotes, hidden, genres, stars);
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
    public String getBackdrop_path() {
        return backdrop_path;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Integer getBudget() {
        return budget;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getOverview() {
        return overview;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getPoster_path() {
        return poster_path;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @SuppressWarnings("unused") //Used by Jackson

    public Integer getRevenue() {
        return revenue;
    }

    @SuppressWarnings("unused") //Used by Jackson

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
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

    @SuppressWarnings("unused") //Used by Jackson
    public List<Genre> getGenres() {
        return genres;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public List<Star> getStars() {
        return stars;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setStars(List<Star> stars) {
        this.stars = stars;
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
                ", year=" + year +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", budget=" + budget +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", revenue=" + revenue +
                ", rating=" + rating +
                ", numVotes=" + numVotes +
                ", genres=" + genres +
                ", stars=" + stars +
                '}';
    }
}
