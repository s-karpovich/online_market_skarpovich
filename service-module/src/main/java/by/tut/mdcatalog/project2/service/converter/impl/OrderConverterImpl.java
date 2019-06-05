package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.repository.model.Order;
import by.tut.mdcatalog.project2.repository.model.Status;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.OrderConverter;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;
import by.tut.mdcatalog.project2.service.model.StatusDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class OrderConverterImpl implements OrderConverter {

    @Override
    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDate(order.getDate());
        orderDTO.setCount(order.getCount());
        orderDTO.setUniqueNumber(order.getUniqueNumber());
        orderDTO.setTotal(order.getTotal());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(order.getUser().getId());
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(order.getItem().getId());
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(order.getStatus().getId());

        orderDTO.setUserDTO(userDTO);
        orderDTO.setItemDTO(itemDTO);
        orderDTO.setStatusDTO(statusDTO);
        return orderDTO;
    }

    @Override
    public Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDate(orderDTO.getDate());
        order.setCount(orderDTO.getCount());
        order.setUniqueNumber(orderDTO.getUniqueNumber());
        order.setTotal(orderDTO.getTotal());
        
        User user = new User();
        user.setId(orderDTO.getUserDTO().getId());
        Item item = new Item();
        item.setId(orderDTO.getItemDTO().getId());
        Status status = new Status();
        status.setId(orderDTO.getStatusDTO().getId());
        
        order.setUser(user);
        order.setItem(item);
        order.setStatus(status);
        return order;
    }
}
