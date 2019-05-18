package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.RoleService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final UserService userService;

    public ProfileController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUser(ModelMap model, Principal principal) {
        UserDTO userDTO = userService.getByUsername(principal.getName());
        model.addAttribute("user", userDTO);
        return "profile";
    }
}

