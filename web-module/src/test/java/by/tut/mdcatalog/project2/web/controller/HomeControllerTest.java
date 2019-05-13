package by.tut.mdcatalog.project2.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private MockMvc mockMvc;

    @Before
    public void init() {
        HomeController controller = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldShowLoginPageForHomeURL() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login"));
    }

    @Test
    public void shouldShowAboutPageForAboutURL() throws Exception {
        this.mockMvc.perform(get("/about.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/about"));
    }
}