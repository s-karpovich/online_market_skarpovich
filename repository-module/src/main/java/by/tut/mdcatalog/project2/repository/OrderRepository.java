package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {

    List<Order> getAllWithOrder();
    List<Order> getByUserIdWithOrder(Long id);
}
