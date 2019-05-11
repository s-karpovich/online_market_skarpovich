package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Feedback;
import by.tut.mdcatalog.project2.service.model.FeedbackDTO;

public interface FeedbackConverter {

    FeedbackDTO toDTO(Feedback feedback);

    Feedback fromDTO(FeedbackDTO feedbackDTO);
}
