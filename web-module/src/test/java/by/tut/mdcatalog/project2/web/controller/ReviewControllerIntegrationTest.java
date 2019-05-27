package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
import by.tut.mdcatalog.project2.web.constant.AuthorizationConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class ReviewControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ReviewController reviewController;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    @Test
    public void shouldShowReviewsPageforAdmin() throws Exception {
        mvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    public void shouldDeleteReviewsForAdmin() {
        Long[] ids = {2L, 3L};
        String url = reviewController.deleteReviews(ids);
        Assert.assertEquals("redirect:/success", url);
    }

    @WithMockUser(authorities = {AuthorizationConstants.CUSTOMER_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfCustomerAccessReviewsPage() throws Exception {
        mvc.perform(get("/reviews"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.CUSTOMER_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfCustomerAccessDeletePage() throws Exception {
        mvc.perform(get("/reviews/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiAccessReviewsPage() throws Exception {
        mvc.perform(get("/reviews"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiAccessReviewPage() throws Exception {
        mvc.perform(get("/reviews/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }
}
