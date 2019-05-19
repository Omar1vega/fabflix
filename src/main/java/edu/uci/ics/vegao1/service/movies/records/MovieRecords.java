package edu.uci.ics.vegao1.service.movies.records;

import edu.uci.ics.vegao1.service.movies.MovieService;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.SearchRequestModel;
import edu.uci.ics.vegao1.service.movies.models.SearchResponseModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRecords {
    public static final String CHECK_GENRE = "SELECT count(*) FROM genres WHERE id = ? AND NAME LIKE ?";
    public static final String INSERT_MOVIE_STATEMENT = "INSERT INTO movies (id, title, year, director, backdrop_path, budget, overview, poster_path, revenue, hidden)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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

    public static void addMovie(Movie movie) {
        try {
            ServiceLogger.LOGGER.info("preparing statement");
            PreparedStatement statement = MovieService.getCon().prepareStatement(INSERT_MOVIE_STATEMENT);

            statement.setString(1, movie.getMovieId());
            statement.setString(2, movie.getTitle());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getDirector());
//            statement.setString(5, movie.getBackdrop_path());
//            statement.setInt(6, movie.getBudget());
//            statement.setString(7, movie.getOverview());
//            statement.setString(8, movie.getPoster_path());
//            statement.setInt(9, movie.getRevenue());
            statement.setInt(10, movie.getHidden() ? 1 : 0);
            ServiceLogger.LOGGER.info("Inserting movie: " + statement.toString());
            statement.execute();


        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to search movies: " + e.getClass() + e.getCause().getLocalizedMessage());
        }
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
