package by.tut.mdcatalog.project2.web;

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
public class MessagesControllerTest {

    private MockMvc mockMvc;

    @Before
    public void init() {
        MessagesController controller = new MessagesController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldShowSuccessPageForSuccessURL() throws Exception {
        this.mockMvc.perform(get("/success.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("messages/success"));
    }

    @Test
    public void shouldShowErrorPageForErrorURL() throws Exception {
        this.mockMvc.perform(get("/error.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/messages/error"));
    }

    @Test
    public void shouldShowError403PageForErrorURL() throws Exception {
        this.mockMvc.perform(get("/403.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/messages/403"));
    }

    @Test
    public void shouldShowError404PageForErrorURL() throws Exception {
        this.mockMvc.perform(get("/404.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/messages/404"));
    }
}