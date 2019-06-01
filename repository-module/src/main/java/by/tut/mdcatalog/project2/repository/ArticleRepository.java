package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> getAllWithOrder();
}
