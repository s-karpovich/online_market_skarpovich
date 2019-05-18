package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Article;
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
public class ArticleRepositoryImpl extends GenericRepositoryImpl implements ArticleRepository {

    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    @Override
    public List<Article> getArticles(Connection connection) {
        String sqlQuery = "SELECT * FROM Article WHERE deleted=false ORDER BY date DESC";  // Get all excepting deleted
        List<Article> ArticleList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ArticleList.add(buildArticle(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return ArticleList;
    }

    @Override
    public Article getById(Connection connection, Long id) {
        String sqlQuery = "SELECT * FROM article WHERE id=? AND deleted=false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildArticle(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return null;
    }


    private Article buildArticle(ResultSet resultSet) throws SQLException {
        Article Article = new Article();
        Article.setId(resultSet.getLong("id"));
        Article.setDate(resultSet.getDate("date"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        Article.setUser(user);
        Article.setName(resultSet.getString("name"));
        Article.setMessage(resultSet.getString("message"));
        Article.setDeleted(resultSet.getBoolean("deleted"));
        return Article;
    }
}
