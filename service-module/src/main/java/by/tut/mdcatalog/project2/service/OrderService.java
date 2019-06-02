package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;

import java.util.List;

public interface OrderService {

    void create(OrderDTO orderDTO);

   // void update(OrderDTO orderDTO);

    //void delete(Long id);

    List<OrderDTO> getOrders();

    OrderDTO getById(Long id);

}
