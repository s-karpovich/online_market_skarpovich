package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.FeedbackDTO;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDTO> getFeedbacks();

    void deleteFeedbacks(int[] ids);
}
