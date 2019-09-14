package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.OrderRepository;
import by.tut.mdcatalog.project2.repository.model.Order;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Order> getAllWithOrder() {
        String hql = "FROM Order as O ORDER BY date DESC";
        Query query = entityManager.createQuery(hql);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> getByUserIdWithOrder(Long id) {
        String hql = "FROM Order as O where user.id=:id ORDER BY date DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
