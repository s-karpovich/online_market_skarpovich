package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getUsers() {
        List<UserDTO> usersDTO =  userService.getUsers();
        logger.info("Requested Users via REST API");
        return new ResponseEntity(usersDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST);
        }
        userService.create(userDTO);
        logger.info("User added via REST API (ID): {}", userDTO.getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
