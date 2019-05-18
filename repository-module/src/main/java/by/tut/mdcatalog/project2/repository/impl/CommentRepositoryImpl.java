package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.CommentRepository;
import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl implements CommentRepository {

    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    @Override
    public List<Comment> getByUserId(Connection connection, Long id) {
        String sqlQuery = "SELECT * FROM comment WHERE article_id=? AND deleted=false ORDER BY date DESC";  // Get all excepting deleted
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    comments.add(buildComment(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return comments;
    }

    private Comment buildComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("id"));
        comment.setDate(resultSet.getDate("date"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        comment.setUser(user);
        Article article = new Article();
        article.setId(resultSet.getLong("article_id"));
        comment.setArticle(article);
        comment.setMessage(resultSet.getString("message"));
        comment.setDeleted(resultSet.getBoolean("deleted"));
        return comment;
    }
}
