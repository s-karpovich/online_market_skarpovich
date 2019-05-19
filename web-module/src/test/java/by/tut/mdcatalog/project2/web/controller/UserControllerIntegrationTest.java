package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
import by.tut.mdcatalog.project2.web.app.SpringBootModuleApp;
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

import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.ADMIN_ROLE_NAME;
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

    @Test
    public void shouldRedirectAdminToUsersPageOnLogin() throws Exception {
        mvc.perform(post("/login")
                .param("username", "admin@email.com")
                .param("password", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void shouldRedirectAdminToLoginErrorPageOnLoginWithWrongData() throws Exception {
        mvc.perform(post("/login")
                .param("username", "admin@email.com")
                .param("password", "wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldShowUsersPageforAdmin() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    public void shouldUpdateUserRole() {
        RoleDTOUpdated roleDTOUpdated = new RoleDTOUpdated();
        roleDTOUpdated.setId(3L);
        roleDTOUpdated.setRoleId(1L);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        String url = userController.changeStatus(roleDTOUpdated, bindingResult);
        Assert.assertEquals("redirect:/success", url);
    }

    @Test
    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    public void shouldResetPassword() {
        String url = userController.resetPassword(2L);
        Assert.assertEquals("redirect:/success", url);
    }

    @Test
    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    public void shouldDeleteUsers() {
        int[] ids = {2, 3};
        String url = userController.deleteUsers(ids);
        Assert.assertEquals("redirect:/success", url);
    }
}
