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
public class UserApiControllerIntegrationTest {

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

        @Test
    @WithMockUser(authorities = {REST_API_ROLE_NAME})
    public void shouldShowUsers() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "rest@email.com",
            password = "1234",
            authorities = {REST_API_ROLE_NAME})
    @Test
    public void shouldAddUser() throws Exception {
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"username\" : \"restTest2@email.com\",\n" +
                        "    \"password\" : \"1234\",\n" +
                        "    \"firstname\" : \"restFirstname\",\n" +
                        "    \"middlename\" : \"restMiddlename\", \n" +
                        "    \"surname\" : \"restSurname\",\n" +
                        "      \"roleDTO\" : { \n" +
                        "           \"id\" : \"1\"\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isCreated());
    }
}
