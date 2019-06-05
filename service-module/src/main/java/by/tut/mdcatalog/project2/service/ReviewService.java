package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviews();

    void deleteReviews(Long[] ids);

    void create(ReviewDTO reviewDTO);
}
