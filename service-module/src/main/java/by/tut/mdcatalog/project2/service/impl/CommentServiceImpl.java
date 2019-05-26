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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<CommentDTO> getByArticleId(Long id) {
        List<CommentDTO> commentsDTO = new ArrayList<>();
        List<Comment> comments = commentRepository.getByArticleId(id);
        Article article = articleRepository.getById(id);
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        for (Comment comment : comments) {
            User user = userRepository.getById(comment.getUser().getId());
            UserDTO userDTO = userConverter.toDTO(user);
            CommentDTO commentDTO = commentConverter.toDTO(comment);
            commentDTO.setArticleDTO(articleDTO);
            commentDTO.setUserDTO(userDTO);
            commentsDTO.add(commentDTO);
        }
        return commentsDTO;
    }
}