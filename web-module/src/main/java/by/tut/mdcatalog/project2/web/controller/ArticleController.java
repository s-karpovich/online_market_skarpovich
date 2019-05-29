package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ItemAPIController.class);
    private final ArticleService articleService;
    private final CommentService commentService;


    public ArticleController(ArticleService articleService,
                             CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleDTO> articlesDTO = articleService.getArticles();
        model.addAttribute("articles", articlesDTO);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        ArticleDTO articleDTO = articleService.getById(id);
        List<CommentDTO> commentsDTO = commentService.getByArticleId(id);
        modelMap.addAttribute("article", articleDTO);
        modelMap.addAttribute("comments", commentsDTO);
        return "article";
    }

    @PostMapping("/articles/{id}")
    public String updateUrticle(@PathVariable(name = "id") Long id,
                                @Valid ArticleDTO articleDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("article", articleDTO);
            logger.info("Article update failed (ID): {}", id);
            return "article";
        }
        articleDTO.setId(id);
        articleDTO.setDate(new Date());
        articleDTO.setUserDTO(articleService.getById(id).getUserDTO());
        articleService.update(articleDTO);
        logger.info("Article updated (ID) {}", id);
        return "redirect:/success";
    }

    @PostMapping("/articles/comments")
    public String deleteComments(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids == null) {
            logger.info("Delete incompleted: no comments selected");
            return "/article";
        } else {
            commentService.deleteComments(ids);
            logger.info("Comments deleted(IDs):{}", ids);
            return "redirect:/success";
        }
    }
}
