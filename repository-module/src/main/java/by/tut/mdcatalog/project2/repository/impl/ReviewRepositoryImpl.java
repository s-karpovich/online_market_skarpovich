package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.ReviewRepository;
import by.tut.mdcatalog.project2.repository.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public List<Review> getReviews(Connection connection) {
        String sqlQuery = "SELECT * FROM review WHERE deleted=false";  // Get all excepting deleted
        List<Review> reviewList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(buildReview(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return reviewList;
    }

    @Override
    public void deleteReviews(Connection connection, int[] ids, String column) {
        StringBuilder sqlQuery = new StringBuilder("UPDATE review SET deleted=true WHERE + " + column + " IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i != ids.length - 1) {
                sqlQuery.append(ids[i]).append(',');
            } else {
                sqlQuery.append(ids[i]).append(')');
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString())) {
            preparedStatement.executeUpdate();
            logger.info("Reviews deleted:{}", ids.length);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }


    private Review buildReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setDate(resultSet.getDate("date"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        review.setUser(user);
        review.setMessage(resultSet.getString("message"));
        review.setDeleted(resultSet.getBoolean("deleted"));
        return review;
    }
}
