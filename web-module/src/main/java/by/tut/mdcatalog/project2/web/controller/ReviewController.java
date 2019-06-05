package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ReviewService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ReviewDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/reviews")
    public String getReviews(Model model) {
        List<ReviewDTO> reviewDTOList = reviewService.getReviews();
        model.addAttribute("reviews", reviewDTOList);
        return "reviews";
    }

    @GetMapping("/reviews/add")
    public String addReview() {
        return "addreview";
    }

    @PostMapping("/reviews/add")
    public String addReview(
            @Valid ReviewDTO reviewDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            logger.info("User has not been added");
            return "redirect:/error"; }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO userDTO = userService.getByUsername(username);
        reviewDTO.setUserDTO(userDTO);
        reviewDTO.setDate(new Date());
        reviewService.create(reviewDTO);
        return "redirect:/success";
    }

    @PostMapping("/reviews/delete")
    public String deleteReviews(@RequestParam(value = "ids", required = false) Long[] ids) {
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
