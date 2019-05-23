package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.service.model.CommentDTO;

public interface CommentConverter {

    CommentDTO toDTO(Comment comment);

    Comment fromDTO(CommentDTO commentDTO);
}
