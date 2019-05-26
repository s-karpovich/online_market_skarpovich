package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.ReviewRepository;
import by.tut.mdcatalog.project2.repository.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void delete(Review review) {
        entityManager.remove(review);
    }

    @Override
    public List<Review> getAll() {
        String hql = "from Review as r";
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

//    @Override
//    public void deleteReviews(int[] ids) {
//        StringBuilder inQuery = new StringBuilder("(");
//        for (int i = 0; i < ids.length; i++) {
//            if (i != ids.length - 1) inQuery.append(ids[i]).append(',');
//            else inQuery.append(ids[i]).append(")");
//        }
//        String hql = "update Review R set deleted=true where id in" + inQuery;
//        Query query = entityManager.createQuery(hql);
//        query.executeUpdate();
//    }
}
