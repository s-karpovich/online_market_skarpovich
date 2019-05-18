//package by.tut.mdcatalog.project2.service.impl;
//
//import by.tut.mdcatalog.project2.repository.ReviewRepository;
//import by.tut.mdcatalog.project2.repository.model.Review;
//import by.tut.mdcatalog.project2.service.ReviewService;
//import by.tut.mdcatalog.project2.service.converter.ReviewConverter;
//import by.tut.mdcatalog.project2.service.model.ReviewDTO;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.sql.Connection;
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//
//
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ReviewServiceUnitTest {
//    @Mock
//    private ReviewRepository reviewRepository;
//    @Mock
//    private ReviewConverter reviewConverter;
//    @Mock
//    private Connection connection;
//    private ReviewService reviewService;
//    private Review firstReview = new Review(null, "text1", new Timestamp(System.currentTimeMillis()), true, false);
//    private Review secondReview = new Review(null, "text2", new Timestamp(System.currentTimeMillis()), false, true);
//    private List<Review> reviewList = Arrays.asList(firstReview, secondReview);
//
//    @Before
//    public void init() {
//        reviewService = new ReviewServiceImpl(reviewConverter, reviewRepository);
//    }
//
//    @Test
//    public void shouldReturnRightReviewDTOsListWhenGetReviewsBatch() {
//        when(reviewRepository.getConnection()).thenReturn(connection);
//        when(reviewRepository.getReviews(connection).thenReturn(reviewList);
//        ReviewDTO firstReviewDTO = new ReviewDTO(null, "text1", firstReview.getCreated(), true, false);
//        ReviewDTO secondReviewDTO = new ReviewDTO(null, "text2", firstReview.getCreated(), false, true);
//        when(reviewConverter.toDTO(firstReview)).thenReturn(firstReviewDTO);
//        when(reviewConverter.toDTO(secondReview)).thenReturn(secondReviewDTO);
//        Assert.assertEquals(firstReviewDTO, reviewService.getReviewsBatch(1).get(0));
//        Assert.assertEquals(secondReviewDTO, reviewService.getReviewsBatch(1).get(1));
//    }
//}
