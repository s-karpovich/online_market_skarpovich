package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Feedback;

import java.sql.Connection;
import java.util.List;

public interface FeedbackRepository {

    List<Feedback> getFeedbacks(Connection connection);

    void deleteFeedbacks(Connection connection, int[] ids);
}
