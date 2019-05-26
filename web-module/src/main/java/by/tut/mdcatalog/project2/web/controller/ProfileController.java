package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ContactService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;
    private final ContactService contactService;

    public ProfileController(UserService userService,
                             ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping("/profile")
    public String getProfile(ModelMap model, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO = userService.getByUsername(username);
        ContactDTO contactDTO = contactService.getByUserId(userDTO.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("contact", contactDTO);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserDTO userDTO,
                                ContactDTO contactDTO,
                                ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO currentUserDTO = userService.getByUsername(username);
        userDTO.setId(currentUserDTO.getId());
        userDTO.setUsername(currentUserDTO.getUsername());
        userDTO.setRoleDTO(currentUserDTO.getRoleDTO());
        userDTO.setDeleted(false);
        contactDTO.setId(userDTO.getId());
        contactDTO.setUserDTO(userDTO);
        contactDTO.setDeleted(false);
        userService.update(userDTO);
        contactService.update(contactDTO);
        modelMap.addAttribute("user", userDTO);
        modelMap.addAttribute("contact", contactDTO);
        return "redirect:/success";
    }
}