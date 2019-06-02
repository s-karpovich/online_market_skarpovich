package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ItemRepository;
import by.tut.mdcatalog.project2.repository.OrderRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.repository.model.Order;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.OrderService;
import by.tut.mdcatalog.project2.service.converter.ItemConverter;
import by.tut.mdcatalog.project2.service.converter.OrderConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public OrderServiceImpl(
            OrderConverter orderConverter,
            OrderRepository orderRepository,
            UserRepository userRepository,
            UserConverter userConverter,
            ItemRepository itemRepository,
            ItemConverter itemConverter) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public void create(OrderDTO orderDTO) {
        Order order = orderConverter.fromDTO(orderDTO);
        orderRepository.persist(order);
    }


    @Override
    @Transactional
    public List<OrderDTO> getOrders() {
        List<OrderDTO> ordersDTO = new ArrayList<>();
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders) {
            User user = userRepository.getById(order.getUser().getId());
            UserDTO userDTO = userConverter.toDTO(user);
            Item item = itemRepository.getById(order.getItem().getId());
            ItemDTO itemDTO = itemConverter.toDTO(item);
            OrderDTO orderDTO = orderConverter.toDTO(order);

            orderDTO.setUserDTO(userDTO);
            orderDTO.setItemDTO(itemDTO);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }

    @Override
    @Transactional
    public OrderDTO getById(Long id) {
        Order order = orderRepository.getById(id);
        OrderDTO orderDTO = orderConverter.toDTO(order);
        User user = userRepository.getById(order.getUser().getId());
        UserDTO userDTO = userConverter.toDTO(user);
        Item item = itemRepository.getById(order.getItem().getId());
        ItemDTO itemDTO = itemConverter.toDTO(item);

        orderDTO.setUserDTO(userDTO);
        orderDTO.setItemDTO(itemDTO);
        return orderDTO;
    }
}