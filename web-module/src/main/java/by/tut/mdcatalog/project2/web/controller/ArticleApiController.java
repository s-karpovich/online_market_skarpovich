package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleApiController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getArticles() {
        articleService.getArticles();
        logger.info("Added Article via REST API");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getArticle(@PathVariable Long id){
        articleService.getById(id);
        logger.info("Added Article via REST API");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addArticle(@RequestBody ArticleDTO articleDTO, Principal principal) {
        String username = principal.getName();
        Long id = userService.getByUsername(username).getId();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        articleDTO.setUserDTO(userDTO);
        articleDTO.setDate(new Date());
        articleService.add(articleDTO);
        logger.info("Added User via REST API");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        logger.info("Deleted User via REST API");
        return new ResponseEntity(HttpStatus.OK);
    }
}
