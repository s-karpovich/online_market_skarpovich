package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.CommentRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.CommentService;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.converter.CommentConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

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

    @Override
    @Transactional
    public void deleteComments(Long[] ids) {
        for (Long id : ids) {
            Comment comment = commentRepository.getById(id);
            if (comment != null && !comment.getDeleted()) {
                commentRepository.remove(comment);
            }
        }
    }
}