package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Order;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;

public interface OrderConverter {

    OrderDTO toDTO(Order order);

    Order fromDTO(OrderDTO orderDTO);
}
