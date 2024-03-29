package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.Order;

import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.OrderService;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable long userId) {
        return service.findAllOrdersByUserId(userId);

    }
    @GetMapping("/cart/user/{userId}")
    public String getOrdersInTheCartByUserId(@PathVariable long userId, Model model) {
        User user = userService.findById(userId);
        Long orderId = 0L;
        List<Order> ordersInTheCart = new ArrayList<>();
        Double total = 0.0;
        for(Order order : user.getOrders()){
            if(order.getOrderStatus().getCode() == 1){
                ordersInTheCart.add(order);
                total += order.getTotal();
                orderId = order.getId();
            }
        }

        model.addAttribute("orderId", orderId);
        model.addAttribute("user", user);
        model.addAttribute("orderTotal", total);
        model.addAttribute("orders", ordersInTheCart);
        return "cart";

    }

    @GetMapping("/cart/checkout/{orderId}")
    public String checkoutOrdersInTheCart(@PathVariable long orderId, Model model) {
        var order = service.findById(orderId);
        var user = order.getUser();
        model.addAttribute("user", user);
        if(order.getOrderStatus().getCode() == 1) {
            model.addAttribute("orders", order);

        }
        return "checkout";
    }

    @GetMapping("/thank-you")
    public String thankYouPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("user-id");
        if (userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            model.addAttribute("user", user);
        }
        return "thank-you";
    }

}
