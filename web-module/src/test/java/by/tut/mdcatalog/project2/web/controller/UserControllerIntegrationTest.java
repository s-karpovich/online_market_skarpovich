package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
import by.tut.mdcatalog.project2.web.constant.AuthorizationConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApp.class)
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Mock
    BindingResult bindingResult;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    @Test
    public void shouldShowUsersPageforAdmin() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin@email.com",
            password = "admin",
            authorities = AuthorizationConstants.ADMIN_ROLE_NAME
    )
    @Test
    public void shouldAddUserbyAdmin() throws Exception {
        mvc.perform(post("/users/add")
                .param("username", "testuser4@email.com")
                .param("password", "user")
                .param("firstname", "User4")
                .param("middlename", "Userovich4")
                .param("surname", "Userov4")
                .param("role", "CUSTOMER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/success"));
    }

    @Test
    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    public void shouldUpdateUserRole() {
        RoleDTOUpdated roleDTOUpdated = new RoleDTOUpdated();
        roleDTOUpdated.setId(4L);
        roleDTOUpdated.setRoleId(1L);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        String url = userController.changeStatus(roleDTOUpdated, bindingResult);
        Assert.assertEquals("redirect:/success", url);
    }

    @Test
    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    public void shouldResetPassword() {
        String url = userController.resetPassword(4L);
        Assert.assertEquals("redirect:/success", url);
    }

    @Test
    @WithMockUser(authorities = {AuthorizationConstants.ADMIN_ROLE_NAME})
    public void shouldDeleteUsers() {
        int[] ids = {4};
        String url = userController.deleteUsers(ids);
        Assert.assertEquals("redirect:/success", url);
    }

    @WithMockUser(authorities = {AuthorizationConstants.USER_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfCustomerAccessAddPage() throws Exception {
        mvc.perform(get("/users/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.USER_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfCustomerAccessUsersPage() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiAccessUsersPage() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = {AuthorizationConstants.REST_API_ROLE_NAME})
    @Test
    public void shouldRedirectTo403PageIfRestApiAccessAddPage() throws Exception {
        mvc.perform(get("/users/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }
}
