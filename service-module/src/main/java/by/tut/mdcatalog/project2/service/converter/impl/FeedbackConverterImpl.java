package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Feedback;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.FeedbackConverter;
import by.tut.mdcatalog.project2.service.model.FeedbackDTO;
import org.springframework.stereotype.Component;

@Component
public class FeedbackConverterImpl implements FeedbackConverter {

    @Override
    public FeedbackDTO toDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setDate(feedback.getDate());
        feedbackDTO.setMessage(feedback.getMessage());
        feedbackDTO.setDeleted(feedback.getDeleted());
        return feedbackDTO;
    }

    @Override
    public Feedback fromDTO(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackDTO.getId());
        feedback.setDate(feedbackDTO.getDate());
        User user = new User();
        user.setUsername(feedbackDTO.getUserDTO().getUsername());
        feedback.setUser(user);
        feedback.setMessage(feedbackDTO.getMessage());
        feedback.setDeleted(feedbackDTO.getDeleted());
        return feedback;
    }
}
