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
    public List<ReviewDTO> getReviews() {
        try (Connection connection = reviewRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<ReviewDTO> reviewDTOList = new ArrayList<>();
                List<Review> reviewList = reviewRepository.getReviews(connection);
                for (Review review : reviewList) {
                    User user = userRepository.getById(connection, review.getUser().getId());
                    UserDTO userDTO = userConverter.toDTO(user);
                    ReviewDTO reviewDTO = reviewConverter.toDTO(review);
                    reviewDTO.setUserDTO(userDTO);
                    reviewDTOList.add(reviewDTO);
                }
                connection.commit();
                return reviewDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void deleteReviews(int[] ids) {
        try (Connection connection = reviewRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                reviewRepository.deleteReviews(connection, ids, "id");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }
}