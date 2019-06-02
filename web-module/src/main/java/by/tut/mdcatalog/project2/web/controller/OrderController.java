package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.*;
import by.tut.mdcatalog.project2.service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ContactService contactService;
    private final UserService userService;


    public OrderController(OrderService orderService,
                           ContactService contactService,
                           UserService userService) {
        this.orderService = orderService;
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<OrderDTO> ordersDTO = orderService.getOrders();
        model.addAttribute("orders", ordersDTO);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrderyId(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        OrderDTO orderDTO = orderService.getById(id);
        ContactDTO contactDTO = contactService.getByUserId(orderDTO.getUserDTO().getId());
        modelMap.addAttribute("order", orderDTO);
        modelMap.addAttribute("contact", contactDTO);
        return "order";
    }

}
