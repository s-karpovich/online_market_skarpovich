package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleDTO> articlesDTO = articleService.getAll();
        model.addAttribute("articles", articlesDTO);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle (@PathVariable(value = "id") Long id, ModelMap modelMap) {
        ArticleDTO articleDTO = articleService.getById(id);
        List<CommentDTO> commentsDTO = commentService.getByArticleId(id);
        modelMap.addAttribute("article", articleDTO);
        modelMap.addAttribute("comments", commentsDTO);
        return "article";
    }
}
