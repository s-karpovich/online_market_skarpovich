package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.FeedbackService;
import by.tut.mdcatalog.project2.service.model.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedbacks")
    public String getFeedbacks(Model model) {
        List<FeedbackDTO> feedbackDTOList = feedbackService.getFeedbacks();
        model.addAttribute("feedbacks", feedbackDTOList);
        return "feedbacks";
    }

    @PostMapping("/feedbacks")
    public String deleteFeedbacks(@RequestParam(value = "ids", required = false) int[] ids) {
        if (ids == null) {
            logger.info("no feedbacks selected");
            return "/feedbacks";
        } else {
            feedbackService.deleteFeedbacks(ids);
            logger.info("Feedbacks deleted(IDs):{}", ids);
            return "redirect:/feedbacks";
        }
    }
}
