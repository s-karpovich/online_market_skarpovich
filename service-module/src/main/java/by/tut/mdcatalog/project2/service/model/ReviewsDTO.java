package by.tut.mdcatalog.project2.service.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ReviewsDTO {
    @NotNull
    private List<ReviewDTO> reviews;

    public ReviewsDTO() {

    }

    public ReviewsDTO(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}