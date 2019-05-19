package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.ArticleService;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
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
    public List<ArticleDTO> getArticles() {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<ArticleDTO> articleDTOList = new ArrayList<>();
                List<Article> articleList = articleRepository.getArticles(connection);
                for (Article article : articleList) {
                    User user = userRepository.getById(connection, article.getUser().getId());
                    UserDTO userDTO = userConverter.toDTO(user);
                    ArticleDTO articleDTO = articleConverter.toDTO(article);
                    articleDTO.setUserDTO(userDTO);
                    articleDTOList.add(articleDTO);
                }
                connection.commit();
                return articleDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public ArticleDTO getById(Long id) {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Article article = articleRepository.getById(connection, id);
                User user = userRepository.getById(connection, article.getUser().getId());
                ArticleDTO articleDTO = articleConverter.toDTO(article);
                UserDTO userDTO = userConverter.toDTO(user);
                articleDTO.setUserDTO(userDTO);
                connection.commit();
                return articleDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void add(ArticleDTO articleDTO) {
        Article article = articleConverter.fromDTO(articleDTO);
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                articleRepository.add(connection, article);
                connection.commit();
                logger.info("Article added (ID): {}", articleDTO.getId());
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    public void delete(Long id) {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                articleRepository.delete(connection, id);
                connection.commit();
                logger.info("Article deleted (ID): {}", id);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (
                SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }
}