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

import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.ADMIN_ROLE_NAME;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.CUSTOMER_ROLE_NAME;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.REST_API_ROLE_NAME;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.SALE_ROLE_NAME;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

    @WithMockUser(authorities = {CUSTOMER_ROLE_NAME})
    @Test
    public void shouldShowArticlesPageForCustomer() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldShowArticlesPageForSale() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "customer@email.com",
            password = "1234",
            authorities = CUSTOMER_ROLE_NAME)
    @Test
    public void shouldShowArticlePageForCustomer() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "sale@email.com",
            password = "1234",
            authorities = SALE_ROLE_NAME)
    @Test
    public void shouldShowArticlePageForSale() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfAdminAccessArticlesPage() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfAdminAccessArticlePage() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiAccessArticlesPage() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiccessArticlePage() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }
}
