package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;


    public ArticleController(ArticleService articleService,
                             CommentService commentService,
                             UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleDTO> articlesDTO = articleService.getArticles();
        model.addAttribute("articles", articlesDTO);
        return "articles";
    }

    @GetMapping("/articles/add")
    public String getAddArticle(ArticleDTO articleDTO, Model model) {
        model.addAttribute(articleDTO);
        List<ArticleDTO> articlesDTO = articleService.getArticles();
        model.addAttribute("articles", articlesDTO);
        return "addarticle";
    }

    @PostMapping("/articles/add")
    public String addArticle(
            @ModelAttribute ArticleDTO articleDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            logger.info("Article has not been added");
            return "redirect:/error"; }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO userDTO = userService.getByUsername(username);
        articleDTO.setUserDTO(userDTO);
     //   articleDTO.setDate(new Date());
        articleService.create(articleDTO);
        logger.info("Created article (ID): {}", articleDTO.getId());
        return "redirect:/success";
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
            logger.info("Comments deleted (IDs):{}", ids);
            return "redirect:/success";
        }
    }
}
