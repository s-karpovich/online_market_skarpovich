package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ReviewService;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String getReviews(Model model) {
        List<ReviewDTO> reviewDTOList = reviewService.getReviews();
        model.addAttribute("reviews", reviewDTOList);
        return "reviews";
    }

    @PostMapping("/reviews/delete")
    public String deleteReviews(@RequestParam(value = "ids", required = false) int[] ids) {
        if (ids == null) {
            logger.info("no reviews selected");
            return "/reviews";
        } else {
            reviewService.deleteReviews(ids);
            logger.info("Reviews deleted(IDs):{}", ids);
            return "redirect:/success";
        }
    }
}
