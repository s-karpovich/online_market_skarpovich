package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
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
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.USER_ROLE_NAME;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class ArticleControllerIntegrationTest {


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {USER_ROLE_NAME})
    @Test
    public void shouldShowArticlesPageForCustomer() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {USER_ROLE_NAME})
    @Test
    public void shouldShowArticlePageForCustomer() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user@email.com",
            password = "user",
            authorities = USER_ROLE_NAME
    )
    @Test
    public void shouldShowProfilePageForCustomer() throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }
}
