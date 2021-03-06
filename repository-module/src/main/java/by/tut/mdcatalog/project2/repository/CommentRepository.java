package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Comment;

import java.util.List;

public interface CommentRepository extends GenericRepository<Long, Comment> {

    Comment getById(Long id);

    List<Comment> getByArticleId(Long id);
}
