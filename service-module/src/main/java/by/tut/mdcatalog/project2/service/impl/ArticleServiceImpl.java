package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleConverter articleConverter;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public ArticleServiceImpl(
            ArticleConverter articleConverter,
            ArticleRepository articleRepository,
            UserRepository userRepository,
            UserConverter userConverter) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public void create(ArticleDTO articleDTO) {
        Article article = articleConverter.fromDTO(articleDTO);
        articleRepository.persist(article);
    }

    @Override
    @Transactional
    public void update(ArticleDTO articleDTO) {
        Article article = articleConverter.fromDTO(articleDTO);
        articleRepository.merge(article);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Article article = new Article();
        article.setId(id);
        articleRepository.delete(article);
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticles() {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        List<Article> articleList = articleRepository.getAll();
        for (Article article : articleList) {
            User user = userRepository.getById(article.getUser().getId());
            UserDTO userDTO = userConverter.toDTO(user);
            ArticleDTO articleDTO = articleConverter.toDTO(article);
            articleDTO.setUserDTO(userDTO);
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }

    @Override
    @Transactional
    public ArticleDTO getById(Long id) {
        Article article = articleRepository.getById(id);
        User user = userRepository.getById(article.getUser().getId());
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        UserDTO userDTO = userConverter.toDTO(user);
        articleDTO.setUserDTO(userDTO);
        return articleDTO;
    }
}