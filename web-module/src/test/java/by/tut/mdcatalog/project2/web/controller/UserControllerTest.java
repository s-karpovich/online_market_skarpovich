package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import com.gmail.aperavoznikau.springbootsecure.service.UserService;
import com.gmail.aperavoznikau.springbootsecure.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private List<UserDTO> users = asList(new UserDTO(1L, "username1", "password", "firstname", "middlename","surname", false, 2),
            new UserDTO(2L, "username2", "password", "firstname","middlename","surname", false, "ADMINISTRATOR");

    @Before
    public void init() {
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetUsersPage() throws Exception {
        when(userService.getUsers()).thenReturn(users);
        this.mockMvc.perform(get("/private/users.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", users))
                .andExpect(forwardedUrl("users"));
    }
}