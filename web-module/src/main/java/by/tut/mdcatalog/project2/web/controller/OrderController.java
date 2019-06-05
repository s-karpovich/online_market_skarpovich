package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.repository.model.Order;
import by.tut.mdcatalog.project2.service.ContactService;
import by.tut.mdcatalog.project2.service.OrderService;
import by.tut.mdcatalog.project2.service.StatusService;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.OrderDTO;
import by.tut.mdcatalog.project2.service.model.StatusDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ContactService contactService;
    private final UserService userService;
    private final StatusService statusService;

    public OrderController(OrderService orderService,
                           ContactService contactService,
                           UserService userService,
                           StatusService statusService) {
        this.orderService = orderService;
        this.contactService = contactService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/orders")
    public String getOrders(Model model, OrderDTO orderDTO) {
        List<OrderDTO> ordersDTO = orderService.getOrders();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO userDTO = userService.getByUsername(username);
        List<OrderDTO> userOrdersDTO = orderService.getByUserId(userDTO.getId());
        List<StatusDTO> statusesDTO = statusService.getStatuses();
        model.addAttribute("orders", ordersDTO);
        model.addAttribute(orderDTO);
        model.addAttribute("userorders", userOrdersDTO);
        model.addAttribute("statuses", statusesDTO);

        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        OrderDTO orderDTO = orderService.getById(id);
        ContactDTO contactDTO = contactService.getByUserId(orderDTO.getUserDTO().getId());
        modelMap.addAttribute("order", orderDTO);
        modelMap.addAttribute("contact", contactDTO);
        return "order";
    }

    @PostMapping("/orders/{id}")
    public String updateOrder(@PathVariable(name = "id") Long id,
                              @ModelAttribute OrderDTO orderDTO,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("order", orderDTO);
            logger.info("Order update failed (ID): {}", id);
            return "orders";
        }
        OrderDTO dto = orderService.getById(id);
        dto.setStatusDTO(orderDTO.getStatusDTO());
        orderService.update(dto);
        logger.info("Order updated (ID) {}", id);
        return "redirect:/success";
    }
}
