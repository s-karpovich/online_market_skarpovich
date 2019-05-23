package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
import by.tut.mdcatalog.project2.web.constant.AuthorizationConstants;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class ProfileControllerIntegrationTest {


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

    @WithMockUser(username = "user@email.com",
            password = "user",
            authorities = AuthorizationConstants.USER_ROLE_NAME
    )
    @Test
    public void shouldShowProfilePageForCustomer() throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user@email.com",
            password = "user",
            authorities = AuthorizationConstants.USER_ROLE_NAME
    )
    @Test
    public void shouldUpdateUserProfileForCustomer() throws Exception {
        mvc.perform(post("/profile/update")
                .param("firstname", "testFirstname")
                .param("middle", "testMiddleName")
                .param("surname", "testSurname")
                .param("password", "user")
                .param("phone", "testPhone")
                .param("address", "TestAddress"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/success"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageforAdminIfAccessProfilePage() throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageforAdminIfUpdatesProfilePage() throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageForRestApiIfAccessProfilePage() throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldReturnTo405StatusForRestApiIfUpdatesProfilePage() throws Exception {
        mvc.perform(get("/profile/update"))
                .andExpect(status().is4xxClientError());
    }
}
