package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.OrderService;
import by.tut.mdcatalog.project2.service.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    private static final Logger logger = LoggerFactory.getLogger(OrderApiController.class);
    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity getOrders() {
        List<OrderDTO> ordersDTO = orderService.getOrders();
        logger.info("Requested Orders via REST API");
        return new ResponseEntity(ordersDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable Long id){
        OrderDTO orderDTO = orderService.getById(id);
        logger.info("Requested Order via REST API");
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }
}
