package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ReviewRepository;
import by.tut.mdcatalog.project2.repository.model.Review;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void delete(Review review) {
        entityManager.remove(review);
    }

    @Override
    public List<Review> getAll() {
        String hql = "from Review as R";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Review getById(Long id) {
        String hql = "from Review as R where R.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (Review) query.getSingleResult();
    }
}
