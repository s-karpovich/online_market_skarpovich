package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Review;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;

public interface ReviewConverter {

    ReviewDTO toDTO(Review review);

    Review fromDTO(ReviewDTO reviewDTO);
}
