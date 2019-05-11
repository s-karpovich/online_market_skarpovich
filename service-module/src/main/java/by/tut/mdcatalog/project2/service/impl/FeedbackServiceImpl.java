package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.FeedbackRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.connection.ConnectionService;
import by.tut.mdcatalog.project2.repository.model.Feedback;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.FeedbackConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.FeedbackDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    private final ConnectionService connectionService;
    private final FeedbackConverter feedbackConverter;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;


    public FeedbackServiceImpl(ConnectionService connectionService,
                               FeedbackConverter feedbackConverter,
                               FeedbackRepository feedbackRepository,
                               UserRepository userRepository,
                               UserConverter userConverter) {
        this.connectionService = connectionService;
        this.feedbackConverter = feedbackConverter;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<FeedbackDTO> getFeedbacks() {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<FeedbackDTO> feedbackDTOList = new ArrayList<>();
                List<Feedback> feedbackList = feedbackRepository.getFeedbacks(connection);
                for (Feedback feedback : feedbackList) {
                    User user = userRepository.getById(connection, feedback.getUser().getId());
                    if (user == null) continue;     // Skip to show feedbacks of deleted users
                    UserDTO userDTO = userConverter.toDTO(user);
                    FeedbackDTO feedbackDTO = feedbackConverter.toDTO(feedback);
                    feedbackDTO.setUserDTO(userDTO);
                    feedbackDTOList.add(feedbackDTO);
                }
                connection.commit();
                return feedbackDTOList;
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
    public void deleteFeedbacks(int[] ids) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                feedbackRepository.deleteFeedbacks(connection, ids);
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