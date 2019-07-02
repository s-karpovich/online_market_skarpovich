package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static by.tut.mdcatalog.project2.service.constant.DateTimeConstant.DATE_PATTERN;

@Component
public class ArticleConverterImpl implements ArticleConverter {
    private static final Logger logger = LoggerFactory.getLogger(ArticleConverterImpl.class);
    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setDate(new SimpleDateFormat(DATE_PATTERN).format(article.getDate()));
        articleDTO.setName(article.getName());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(article.getUser().getId());
        articleDTO.setUserDTO(userDTO);
        articleDTO.setMessage(article.getMessage());
        return articleDTO;
    }

    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        try {
            article.setDate(new SimpleDateFormat(DATE_PATTERN).parse(articleDTO.getDate()));
        } catch (ParseException e) {
            logger.info("Date parse error");
        }
        User user = new User();
        user.setId(articleDTO.getUserDTO().getId());
        article.setUser(user);
        article.setName(articleDTO.getName());
        article.setMessage(articleDTO.getMessage());
        return article;
    }
}
