package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleAPIController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;

    public ArticleAPIController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity getArticles() {
        articleService.getArticles();
        logger.info("Added article with REST API");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getArticle(@RequestParam Long id){
        articleService.getById(id);
        logger.info("Added article with REST API");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.add(articleDTO);
        logger.info("Added user with REST API");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity addArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.add(articleDTO);
        logger.info("Added user with REST API");
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
