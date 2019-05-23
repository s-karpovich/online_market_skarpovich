package by.tut.mdcatalog.project2.web.controller;


import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.REST_API_ROLE_NAME;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class ArticleApiControllerIntegrationTest {

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

    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldShowArticles() throws Exception {
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldShowArticle() throws Exception {
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "rest@email.com",
            password = "user",
            authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldAddArticle() throws Exception {
        mvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"user_id\": \"3\", " +
                        "\"name\": \"Test Article Name\", " +
                        "\"message\": \"Test Article Message\" " +
                        "}"))
                .andExpect(status().isCreated());
    }


    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldDeleteArticle() throws Exception {
        mvc.perform(post("/api/articles/5"))
                .andExpect(status().isOk());
    }
}
