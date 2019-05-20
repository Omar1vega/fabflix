package edu.uci.ics.vegao1.service.movies.records;

import edu.uci.ics.vegao1.service.movies.MovieService;
import edu.uci.ics.vegao1.service.movies.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.movies.models.StarRequestModel;
import edu.uci.ics.vegao1.service.movies.models.StarResponseModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StarRecords {
    private static final String DEFAULT_ORDER_BY = "name";
    private static final String DEFAULT_SORT = "ASC";
    private static final String GROUP_BY = "GROUP BY stars.id\n";

    private static final String SEARCH_STARS_STATEMENT = "" +
            "SELECT stars.*, group_concat(DISTINCT concat(movies.title, ':', movies.id) SEPARATOR ', ') AS movies\n" +
            "FROM stars,\n" +
            "     stars_in_movies,\n" +
            "     movies\n" +
            "WHERE stars.name LIKE ?\n" +
            "  AND movies.title LIKE ?\n" +
            "  AND stars.id LIKE ?\n" +
            "  AND movies.hidden <= ?\n" +
            "  AND stars.id = stars_in_movies.starId\n" +
            "  AND stars_in_movies.movieId = movies.id\n";

    public static StarResponseModel searchStars(StarRequestModel request, boolean canAccessHidden) {
        ServiceLogger.LOGGER.info("searchStars");

        boolean yearProvided = isValid(request.getBirthYear());
        String yearClause = yearProvided ? "  AND stars.birthYear = ?\n" : "";
        String orderBy = isValid(request.getOrderby()) ? request.getOrderby() : DEFAULT_ORDER_BY;
        String sort = isValid(request.getDirection()) ? request.getDirection() : DEFAULT_SORT;
        int offset = isValid(request.getOffset()) ? request.getOffset() : 0;
        int limit = isValid(request.getLimit()) ? request.getLimit() : 10;

        String query = SEARCH_STARS_STATEMENT + yearClause + " " + GROUP_BY + " ORDER BY " + orderBy + " " + sort + " LIMIT " + offset + ", " + limit;
        try {
            ServiceLogger.LOGGER.info("preparing statement");
            PreparedStatement statement = MovieService.getCon().prepareStatement(query);

            if (yearProvided) {
                statement.setInt(5, request.getBirthYear());
            }

            statement.setString(1, substring(request.getName()));
            statement.setString(2, substring(request.getMovieTitle()));
            statement.setString(3, substring(request.getId()));
            statement.setInt(4, canAccessHidden ? 1 : 0);
            ServiceLogger.LOGGER.info("Searching stars: " + statement.toString());


            ResultSet resultSet = statement.executeQuery();
            ServiceLogger.LOGGER.info("Statement executed");
            List<StarInMovie> stars = new ArrayList<>();
            while (resultSet.next()) {
                ServiceLogger.LOGGER.info("Star Found");
                stars.add(StarInMovie.fromResultSet(resultSet));
            }

            if (!stars.isEmpty()) {
                return new StarResponseModel(212, "Found stars with search parameters.", stars);
            } else {
                return new StarResponseModel(213, "No stars found with search parameters.");
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to search movies: " + e.getClass() + e.getCause().getLocalizedMessage());
        }
        return null;
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
