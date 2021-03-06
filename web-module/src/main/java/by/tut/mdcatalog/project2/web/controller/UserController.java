package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.RoleService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.RoleDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getUsers(Model model, RoleDTOUpdated roleDTOUpdated) {
        List<UserDTO> userDTOList = userService.getUsers();
        List<RoleDTO> roleDTOList = roleService.getRoles();
        model.addAttribute("users", userDTOList);
        model.addAttribute("roles", roleDTOList);
        model.addAttribute("roleDTOUpdatedObject", roleDTOUpdated);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUser(UserDTO userDTO, Model model) {
        model.addAttribute(userDTO);
        List<RoleDTO> rolesDTO = roleService.getRoles();
        model.addAttribute("roles", rolesDTO);
        return "add";
    }

    @PostMapping("/users/add")
    public String addUser(
            @ModelAttribute UserDTO userDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            logger.info("User has not been added");
            return "redirect:/error"; }

            userService.create(userDTO);
            return "redirect:/success";
        }

    @PostMapping("/users/reset")
    public String resetPassword(@RequestParam(value = "id", required = false) Long id) {
        userService.resetPassword(id);
        logger.info("User password reset (ID):{}", id);
        return "redirect:/success";
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids == null) {
            logger.info("Delete not processed: no users selected");
            return "/users";
        } else {
            userService.deleteUsers(ids);
            logger.info("Users deleted(IDs):{}", ids);
            return "redirect:/success";
        }
    }

    @PostMapping("/users/update")
    public String changeStatus(@ModelAttribute(value = "roleDTOUpdatedObject")
                               @Valid RoleDTOUpdated roleDTOUpdated,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Update not processed");
            return "/users";
        }
        userService.updateUserRole(roleDTOUpdated);
        logger.info("User updated (ID): {}", roleDTOUpdated.getId());
        return "redirect:/success";
    }
}

