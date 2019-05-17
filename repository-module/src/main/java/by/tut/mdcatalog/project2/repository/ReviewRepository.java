package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository  extends GenericRepository {

    List<Review> getReviews(Connection connection);

    void deleteReviews(Connection connection, int[] ids, String column);
}
