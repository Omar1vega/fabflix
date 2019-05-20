package edu.uci.ics.vegao1.service.movies.records;

import edu.uci.ics.vegao1.service.movies.MovieService;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.*;
import edu.uci.ics.vegao1.service.movies.util.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRecords {
    public static final String INSERT_MOVIE_STATEMENT = "" +
            "INSERT INTO movies (id, title, year, director, backdrop_path, budget, overview, poster_path, revenue, hidden)\n" +
            "SELECT concat('cs', LPAD(max(cast(substring(movies.id, 3) AS DECIMAL)) + 1, 7, '0')), ?, ?, ?, ?, ?, ?, ?, ?, ?\n" +
            "FROM movies\n" +
            "WHERE id LIKE '%cs%';";
    public static final String CHECK_GENRE = "SELECT count(*) FROM genres WHERE id = ? AND NAME LIKE ?";
    private static final String NEW_MOVIE_ID_PREFIX = "cs";
    private static final String DEFAULT_ORDER_BY = "rating";
    private static final String DEFAULT_SORT = "DESC";
    private static final String SEARCH_MOVIES_STATEMENT =
            "SELECT movies.*,\n" +
                    "       ratings.rating,\n" +
                    "       ratings.numVotes,\n" +
                    "       group_concat(DISTINCT concat(stars.name, ':', stars.id) SEPARATOR ', ')   AS stars,\n" +
                    "       group_concat(DISTINCT concat(genres.name, ':', genres.id) SEPARATOR ', ') AS genres\n" +
                    "FROM movies,\n" +
                    "     ratings,\n" +
                    "     genres_in_movies,\n" +
                    "     genres,\n" +
                    "     stars_in_movies,\n" +
                    "     stars\n" +
                    "WHERE(genres.name LIKE ?)\n" +
                    "  AND (movies.title LIKE ?)\n" +
//                    "  AND (movies.director LIKE ?)\n" +
                    "  AND movies.hidden <= ?\n" +
                    "  AND (movies.id LIKE ?)\n" +
                    "  AND movies.id = ratings.movieId\n" +
                    "  AND stars_in_movies.movieId = movies.id\n" +
                    "  AND stars.id = stars_in_movies.starId\n" +
                    "  AND genres_in_movies.movieId = movies.id\n" +
                    "  AND genres.id = genres_in_movies.genreId\n" +
                    "GROUP BY movies.id\n";

    private static final String HIDE_MOVIE_STATMENT = "" +
            "UPDATE movies\n" +
            "SET hidden = 1\n" +
            "WHERE id = ? ;";

    public static SearchResponseModel searchMovies(SearchRequestModel request, boolean showHidden) {
        ServiceLogger.LOGGER.info("searchMovies");
        String orderBy = isValid(request.getOrderby()) ? request.getOrderby() : DEFAULT_ORDER_BY;
        String sort = isValid(request.getDirection()) ? request.getDirection() : DEFAULT_SORT;
        int offset = isValid(request.getOffset()) ? request.getOffset() : 0;
        int limit = isValid(request.getLimit()) ? request.getLimit() : 10;
        ServiceLogger.LOGGER.info("limits set");

        String query = SEARCH_MOVIES_STATEMENT + " ORDER BY " + orderBy + " " + sort + " LIMIT " + offset + ", " + limit;
        try {
            ServiceLogger.LOGGER.info("preparing statement");
            PreparedStatement statement = MovieService.getCon().prepareStatement(query);

            statement.setString(1, substring(request.getGenre()));
            statement.setString(2, substring(request.getTitle()));
            statement.setInt(3, showHidden ? 1 : 0);
            statement.setString(4, substring(request.getId()));
            ServiceLogger.LOGGER.info("Searching movies: " + statement.toString());


            ResultSet resultSet = statement.executeQuery();
            ServiceLogger.LOGGER.info("Statement executed");
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                ServiceLogger.LOGGER.info("Movie Found");
                Movie movie = Movie.fromResultSet(resultSet);
                if (!showHidden) {
                    movie.hide();
                }
                movies.add(movie);
            }

            if (!movies.isEmpty()) {
                return new SearchResponseModel(210, "Found movies with search parameters.", movies);
            } else {
                return new SearchResponseModel(211, "No movies found with search parameters.");
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to search movies: " + e.getClass() + e.getCause().getLocalizedMessage());
        }
        return null;
    }

    public static SearchFullResponseModel searchFullMovies(SearchRequestModel request, boolean showHidden) {
        ServiceLogger.LOGGER.info("searchMovies");
        String orderBy = isValid(request.getOrderby()) ? request.getOrderby() : DEFAULT_ORDER_BY;
        String sort = isValid(request.getDirection()) ? request.getDirection() : DEFAULT_SORT;
        int offset = isValid(request.getOffset()) ? request.getOffset() : 0;
        int limit = isValid(request.getLimit()) ? request.getLimit() : 10;
        ServiceLogger.LOGGER.info("limits set");

        String query = SEARCH_MOVIES_STATEMENT + " ORDER BY " + orderBy + " " + sort + " LIMIT " + offset + ", " + limit;
        try {
            ServiceLogger.LOGGER.info("preparing statement");
            PreparedStatement statement = MovieService.getCon().prepareStatement(query);

            statement.setString(1, substring(request.getGenre()));
            statement.setString(2, substring(request.getTitle()));
            statement.setInt(3, showHidden ? 1 : 0);
            statement.setString(4, substring(request.getId()));
            ServiceLogger.LOGGER.info("Searching movies: " + statement.toString());


            ResultSet resultSet = statement.executeQuery();
            ServiceLogger.LOGGER.info("Statement executed");
            List<FullMovie> movies = new ArrayList<>();
            while (resultSet.next()) {
                ServiceLogger.LOGGER.info("Movie Found");
                FullMovie movie = FullMovie.fromResultSet(resultSet);
                if (!showHidden) {
                    movie.hide();
                }
                movies.add(movie);
            }

            if (!movies.isEmpty()) {
                return new SearchFullResponseModel(210, "Found movies with search parameters.", movies);
            } else {
                return new SearchFullResponseModel(211, "No movies found with search parameters.");
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to search movies: " + e.getClass() + e.getCause().getLocalizedMessage());
        }
        return null;
    }

    public static void addMovie(FullMovie movie) {
        try {
            ServiceLogger.LOGGER.info("preparing statement");
            PreparedStatement statement = MovieService.getCon().prepareStatement(INSERT_MOVIE_STATEMENT);
            ServiceLogger.LOGGER.info("statemnt statement");
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getBackdrop_path());
            ServiceLogger.LOGGER.info("paths");
            statement.setInt(5, movie.getBudget() == null ? 0 : movie.getBudget());
            statement.setString(6, movie.getOverview());
            statement.setString(7, movie.getPoster_path());
            statement.setInt(8, movie.getRevenue() == null ? 0 : movie.getRevenue());
            ServiceLogger.LOGGER.info("hidden: " + movie.getHidden());
            statement.setInt(9, movie.getHidden() != null && movie.getHidden() ? 1 : 0);
            ServiceLogger.LOGGER.info("Inserting movie: " + statement.toString());
            statement.execute();


        } catch (Exception e) {
            ServiceLogger.LOGGER.info("Unable to search movies: " + e.getClass() + e.getCause().getLocalizedMessage());
        }
    }

    public static ResponseModel hideMovie(String id) throws SQLException {
        boolean movieHid = Db.executeStatement(HIDE_MOVIE_STATMENT, id);
        if (movieHid) {
            return ResponseModel.MOVIE_SUCCESSFULLY_REMOVED;
        } else {
            return ResponseModel.MOVIE_ALREADY_REMOVED;
        }
    }

    public static GenreResponseModel getGenres() throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult("SELECT * FROM genres");
        List<Genre> genres = new ArrayList<>();
        while (resultSet.next()) {
            genres.add(Genre.fromResultSet(resultSet));
        }
        return new GenreResponseModel(ResponseModel.GENRES_SUCCESSFULLY_RETRIEVED, genres);
    }

    public static GenreResponseModel getGenresInMovie(String movieId) throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult("SELECT id, name\n" +
                "FROM genres_in_movies,\n" +
                "     genres\n" +
                "WHERE genres_in_movies.movieId = ? " +
                "  AND genres.id = genres_in_movies.genreId\n" +
                "GROUP BY genres.id;", movieId);
        List<Genre> genres = new ArrayList<>();
        while (resultSet.next()) {
            genres.add(Genre.fromResultSet(resultSet));
        }
        return new GenreResponseModel(ResponseModel.GENRES_SUCCESSFULLY_RETRIEVED, genres);

    }

    public static ResponseModel addGenre(Genre genre) throws SQLException {
        boolean genreAdded = Db.executeStatement("INSERT INTO genres (name) VALUES (?)", genre.getName());
        if (genreAdded) {
            return ResponseModel.GENRE_SUCCESSFULLY_ADDED;
        }
        return ResponseModel.GENRE_COULD_NOT_BE_ADDED;
    }

    private static boolean isValid(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static boolean isValid(Integer i) {
        return i != null && i >= 0;
    }

    private static String substring(String s) {
        ServiceLogger.LOGGER.info("Checking string: " + s);
        if (isValid(s)) {
            ServiceLogger.LOGGER.info("String is valid, ret val:" + "%" + s + "%");
            return "%" + s + "%";
        }
        ServiceLogger.LOGGER.info("String is NOT valid, ret val:" + "%%");
        return "%%";
    }


}
