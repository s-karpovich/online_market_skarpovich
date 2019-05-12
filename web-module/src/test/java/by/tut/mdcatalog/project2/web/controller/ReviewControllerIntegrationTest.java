package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class ReviewControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ReviewController reviewController;
    @Mock
    BindingResult bindingResult;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = "ADMINISTRATOR")
    @Test
    public void shouldShowReviewsPageforAdmin() throws Exception {
        mvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    public void shouldDeleteReviews() {
        int[] ids = {2, 3};
        String url = reviewController.deleteReviews(ids);
        Assert.assertEquals("redirect:/success", url);
    }
}
