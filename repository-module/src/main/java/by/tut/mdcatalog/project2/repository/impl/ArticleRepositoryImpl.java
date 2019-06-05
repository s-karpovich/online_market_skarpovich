package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> getAllWithOrder() {
        String hql = "FROM Article as A ORDER BY date DESC";
        Query query = entityManager.createQuery(hql);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
