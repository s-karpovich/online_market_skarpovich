package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Comment;

import java.util.List;

public interface CommentRepository extends GenericRepository<Long, Comment> {

   // void delete(Comment comment);

    Comment getById(Long id);

    List<Comment> getByArticleId(Long id);
}
