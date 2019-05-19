package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Article;

import java.sql.Connection;
import java.util.List;

public interface ArticleRepository extends GenericRepository {

    List<Article> getArticles(Connection connection);

    Article getById(Connection connection, Long id);

    void add(Connection connection, Article article);
}
