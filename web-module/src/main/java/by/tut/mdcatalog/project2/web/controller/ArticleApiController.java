package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        articleService.getAll();
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
    public ResponseEntity addArticle(@RequestBody ArticleDTO articleDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO userDTO = userService.getByUsername(username);
        userDTO.setDeleted(false);
        articleDTO.setUserDTO(userDTO);
        articleDTO.setDeleted(false);
        articleDTO.setDate(new Date());
        articleService.create(articleDTO);
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
