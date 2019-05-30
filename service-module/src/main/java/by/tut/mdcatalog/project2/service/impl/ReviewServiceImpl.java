package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ReviewRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Review;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.ReviewConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public ReviewServiceImpl(
            ReviewConverter reviewConverter,
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            UserConverter userConverter) {
        this.reviewConverter = reviewConverter;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public void deleteReviews(Long[] ids) {
        for (Long id : ids) {
            Review review = reviewRepository.getById(id);
            if (review != null && !review.getDeleted()) {
                reviewRepository.remove(review);
            }
        }
    }

    @Override
    @Transactional
    public List<ReviewDTO> getReviews() {
        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        List<Review> reviewList = reviewRepository.getAll();
        for (Review review : reviewList) {
                User user = userRepository.getById(review.getUser().getId());
                UserDTO userDTO = userConverter.toDTO(user);
                ReviewDTO reviewDTO = reviewConverter.toDTO(review);
                reviewDTO.setUserDTO(userDTO);
                reviewsDTO.add(reviewDTO);
        }
        return reviewsDTO;
    }
}
