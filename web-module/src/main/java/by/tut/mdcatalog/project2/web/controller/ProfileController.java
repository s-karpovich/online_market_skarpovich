//package by.tut.mdcatalog.project2.web.controller;
//
//import by.tut.mdcatalog.project2.repository.model.User;
//import by.tut.mdcatalog.project2.service.RoleService;
//import by.tut.mdcatalog.project2.service.UserService;
//import by.tut.mdcatalog.project2.service.model.RoleDTO;
//import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
//import by.tut.mdcatalog.project2.service.model.UserDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Controller
//public class ProfileController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
//    private final UserService userService;
//    private final RoleService roleService;
//
//    public ProfileController(UserService userService, RoleService roleService) {
//
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping("/profile")
//    public String getUser (@AuthenticationPrincipal User user, Model model) {
//        UserDTO userDTO = userService.getByUsername(user.getUsername());
//        model.addAttribute("profile", userDTO);
//    return "profile";
//    }
//}
//
