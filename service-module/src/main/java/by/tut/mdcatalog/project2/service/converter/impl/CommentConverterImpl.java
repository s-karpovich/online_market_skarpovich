package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.service.converter.ArticleConverter;
import by.tut.mdcatalog.project2.service.converter.CommentConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {

    private final UserConverter userConverter;
    private final ArticleConverter articleConverter;

    @Autowired
    public CommentConverterImpl(UserConverter userConverter,
                                ArticleConverter articleConverter) {
        this.userConverter = userConverter;
        this.articleConverter = articleConverter;

    }
    
    @Override
    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setUserDTO(userConverter.toDTO(comment.getUser()));
        commentDTO.setMessage(comment.getMessage());
        return commentDTO;
    }

    @Override
    public Comment fromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDate(commentDTO.getDate());
        comment.setUser(userConverter.fromDTO(commentDTO.getUserDTO()));
        comment.setArticle(articleConverter.fromDTO(commentDTO.getArticleDTO()));
        comment.setMessage(commentDTO.getMessage());
        return comment;
    }
}
