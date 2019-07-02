package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Review;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.ReviewConverter;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setMessage(review.getMessage());
        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setDate(reviewDTO.getDate());
        User user = new User();
        user.setUsername(reviewDTO.getUserDTO().getUsername());
        review.setUser(user);
        review.setMessage(reviewDTO.getMessage());
        return review;
    }
}
