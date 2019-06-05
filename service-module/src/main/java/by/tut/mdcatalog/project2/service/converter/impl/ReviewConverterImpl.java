package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Review;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.ReviewConverter;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setMessage(review.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(review.getUser().getId());
        reviewDTO.setUserDTO(userDTO);
        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setDate(reviewDTO.getDate());
        review.setMessage(reviewDTO.getMessage());

        User user = new User();
        user.setId(reviewDTO.getUserDTO().getId());
        review.setUser(user);
        return review;
    }
}
