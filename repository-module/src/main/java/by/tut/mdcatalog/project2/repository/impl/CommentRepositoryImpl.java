package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.CommentRepository;
import by.tut.mdcatalog.project2.repository.model.Comment;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Comment> getByArticleId(Long id) {
        String hql = "from Comment as C where C.article.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
