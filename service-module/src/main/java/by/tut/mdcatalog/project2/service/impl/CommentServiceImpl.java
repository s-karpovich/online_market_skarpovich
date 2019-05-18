package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.CommentRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.converter.CommentConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    public CommentServiceImpl(
                               CommentConverter commentConverter,
                               CommentRepository commentRepository,
                               UserRepository userRepository,
                               UserConverter userConverter,
                               ArticleRepository articleRepository,
                               ArticleConverter articleConverter) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
    }

    @Override
    public List<CommentDTO> getByArticleId(Long id) {
        try (Connection connection = commentRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<CommentDTO> commentDTOList = new ArrayList<>();
                List<Comment> commentList = commentRepository.getByArticleId(connection, id);
                Article article = articleRepository.getById(connection, id);
                ArticleDTO articleDTO = articleConverter.toDTO(article);
                for (Comment comment : commentList) {
                    User user = userRepository.getById(connection, comment.getUser().getId());
                    UserDTO userDTO = userConverter.toDTO(user);
                    CommentDTO commentDTO = commentConverter.toDTO(comment);
                    commentDTO.setArticleDTO(articleDTO);
                    commentDTO.setUserDTO(userDTO);
                    commentDTOList.add(commentDTO);
                }
                connection.commit();
                return commentDTOList;
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
}