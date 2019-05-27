package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository {

    void delete(Review review);

    List<Review> getAll();

    Review getById(Long id);
}
