package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ItemRepository;
import by.tut.mdcatalog.project2.repository.OrderRepository;
import by.tut.mdcatalog.project2.repository.StatusRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.repository.model.Order;
import by.tut.mdcatalog.project2.repository.model.Status;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.OrderService;
import by.tut.mdcatalog.project2.service.converter.ItemConverter;
import by.tut.mdcatalog.project2.service.converter.OrderConverter;
import by.tut.mdcatalog.project2.service.converter.StatusConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;
import by.tut.mdcatalog.project2.service.model.StatusDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final StatusRepository statusRepository;
    private final StatusConverter statusConverter;

    public OrderServiceImpl(
            OrderConverter orderConverter,
            OrderRepository orderRepository,
            UserRepository userRepository,
            UserConverter userConverter,
            ItemRepository itemRepository,
            ItemConverter itemConverter,
            StatusRepository statusRepository,
            StatusConverter statusConverter
    ) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.statusRepository = statusRepository;
        this.statusConverter = statusConverter;
    }

    @Override
    @Transactional
    public void create(OrderDTO orderDTO) {
        orderDTO.setUniqueNumber(UUID.randomUUID().toString().substring(0,6));
        orderDTO.setDate(new Date());
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(1L);
        orderDTO.setStatusDTO(statusDTO);
        Order order = orderConverter.fromDTO(orderDTO);
        orderRepository.persist(order);
    }

    @Override
    @Transactional
    public void update(OrderDTO orderDTO) {
        Order order = orderConverter.fromDTO(orderDTO);
        orderRepository.merge(order);
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders() {
        List<OrderDTO> ordersDTO = new ArrayList<>();
        List<Order> orders = orderRepository.getAllWithOrder();
        for (Order order : orders) {

            User user = userRepository.getById(order.getUser().getId());
            UserDTO userDTO = userConverter.toDTO(user);
            Item item = itemRepository.getById(order.getItem().getId());
            ItemDTO itemDTO = itemConverter.toDTO(item);
            Status status = statusRepository.getById(order.getStatus().getId());
            StatusDTO statusDTO = statusConverter.toDTO(status);
            OrderDTO orderDTO = orderConverter.toDTO(order);

            orderDTO.setUserDTO(userDTO);
            orderDTO.setItemDTO(itemDTO);
            orderDTO.setStatusDTO(statusDTO);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }

    @Override
    @Transactional
    public OrderDTO getById(Long id) {
        Order order = orderRepository.getById(id);

        User user = userRepository.getById(order.getUser().getId());
        UserDTO userDTO = userConverter.toDTO(user);
        Item item = itemRepository.getById(order.getItem().getId());
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Status status = statusRepository.getById(order.getStatus().getId());
        StatusDTO statusDTO = statusConverter.toDTO(status);
        OrderDTO orderDTO = orderConverter.toDTO(order);

        orderDTO.setUserDTO(userDTO);
        orderDTO.setItemDTO(itemDTO);
        orderDTO.setStatusDTO(statusDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public List<OrderDTO> getByUserId(Long id) {
        List<OrderDTO> ordersDTO = new ArrayList<>();
        List<Order> orders = orderRepository.getByUserIdWithOrder(id);
        for (Order order : orders) {
            OrderDTO orderDTO = orderConverter.toDTO(order);
            User user = userRepository.getById(order.getUser().getId());
            UserDTO userDTO = userConverter.toDTO(user);
            Item item = itemRepository.getById(order.getItem().getId());
            ItemDTO itemDTO = itemConverter.toDTO(item);
            Status status = statusRepository.getById(order.getStatus().getId());
            StatusDTO statusDTO = statusConverter.toDTO(status);

            orderDTO.setUserDTO(userDTO);
            orderDTO.setItemDTO(itemDTO);
            orderDTO.setStatusDTO(statusDTO);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }
}