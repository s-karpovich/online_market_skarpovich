package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.FeedbackRepository;
import by.tut.mdcatalog.project2.repository.model.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackRepositoryImpl.class);

    @Override
    public List<Feedback> getFeedbacks(Connection connection) {
        String sqlQuery = "SELECT * FROM feedback WHERE deleted=false";  // Read excepting deleted
        List<Feedback> feedbackList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    feedbackList.add(buildFeedback(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return feedbackList;
    }

    @Override
    public void deleteFeedbacks(Connection connection, int[] ids) {
        StringBuilder sqlQuery = new StringBuilder("UPDATE feedback SET deleted=true WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i != ids.length-1) sqlQuery.append(ids[i]).append(',');
            else  sqlQuery.append(ids[i]).append(')');
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString())) {
            preparedStatement.executeUpdate();
            logger.info("Feedbacks deleted:{}", ids.length);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }

    private Feedback buildFeedback(ResultSet resultSet) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(resultSet.getLong("id"));
        try {
            feedback.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(resultSet.getString("date")));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, e.getMessage()), e);
        }
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        feedback.setUser(user);
        feedback.setMessage(resultSet.getString("message"));
        feedback.setDeleted(resultSet.getBoolean("deleted"));
        return feedback;
    }
}
