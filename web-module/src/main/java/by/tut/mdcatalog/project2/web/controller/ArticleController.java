package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ItemAPIController.class);
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
        List<ArticleDTO> articlesDTO = articleService.getAll();
        model.addAttribute("articles", articlesDTO);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO currentUserDTO = userService.getByUsername(username);

        ArticleDTO articleDTO = articleService.getById(id);
        List<CommentDTO> commentsDTO = commentService.getByArticleId(id);
        modelMap.addAttribute("article", articleDTO);
        modelMap.addAttribute("comments", commentsDTO);
        modelMap.addAttribute("currentuserid", currentUserDTO.getId());
        return "article";
    }

    @PostMapping("/articles/comments")
    public String deleteItems(@RequestParam(value = "ids", required = false) Long[] ids, ModelMap modelMap) {
        if (ids == null) {
            logger.info("Delete incompleted: no comments selected");
            return "/article";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserDTO userDTO = userService.getByUsername(username);
            userDTO.setDeleted(false);
            modelMap.addAttribute("user", userDTO);
            commentService.deleteComments(ids);
            logger.info("Comments deleted(IDs):{}", ids);
            return "redirect:/success";
        }
    }
}
