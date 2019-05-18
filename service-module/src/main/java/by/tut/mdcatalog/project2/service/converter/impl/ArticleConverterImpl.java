package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setDate(article.getDate());
        articleDTO.setName(article.getName());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(article.getUser().getUsername());
        articleDTO.setUserDTO(userDTO);
        articleDTO.setMessage(article.getMessage());
        articleDTO.setDeleted(article.getDeleted());
        return articleDTO;
    }

    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setDate(articleDTO.getDate());
        User user = new User();
        user.setUsername(articleDTO.getUserDTO().getUsername());
        article.setUser(user);
        article.setName(articleDTO.getMessage());
        article.setMessage(articleDTO.getMessage());
        article.setDeleted(articleDTO.getDeleted());
        return article;
    }
}
