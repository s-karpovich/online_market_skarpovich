package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl implements ArticleRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    @Override
    public void persist(Article article) {
        entityManager.persist(article);
    }

    @Override
    public void delete(Article article) {
        entityManager.remove(article);
    }

    @Override
    public List<Article> getAll() {
        String hql = "from Article as A";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Article getById(Long id) {
        String hql = "from Article as A where A.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (Article) query.getSingleResult();
    }
}
