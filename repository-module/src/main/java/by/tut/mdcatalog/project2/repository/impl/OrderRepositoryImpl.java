package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ArticleRepository;
import by.tut.mdcatalog.project2.repository.OrderRepository;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Order> getAllWithOrder() {
        String hql = "FROM Order as O ORDER BY date DESC";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }
}
