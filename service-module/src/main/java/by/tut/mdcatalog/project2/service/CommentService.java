package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getByArticleId(Long id);

    void deleteComments(Long[] ids);
}
