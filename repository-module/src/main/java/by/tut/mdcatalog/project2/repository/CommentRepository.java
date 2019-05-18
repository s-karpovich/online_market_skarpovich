package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Comment;

import java.sql.Connection;
import java.util.List;

public interface CommentRepository extends GenericRepository {

    List<Comment> getByArticleId(Connection connection, Long id);

}
