package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.OrderItem;
import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import com.ecommerce.bicicleta.repositories.UserRepository;
import com.ecommerce.bicicleta.services.OrderService;
import com.ecommerce.bicicleta.services.ProductService;
import com.ecommerce.bicicleta.services.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.http.HttpSession;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @PostMapping("/cart/add-to-the-cart/{id}")
    public ResponseEntity<List<String>> addToTheCart(@RequestBody OrderItem cart, @PathVariable String id, HttpSession session){
        List<String> response = new ArrayList<>();
        //verify if the user is logged in by comparing the session
        String userId = (String) session.getAttribute("user-id");
        System.out.println("User Id: "+userId);
        // if not, return a bad request and redirect to the login page with an alert
        if(userId == null) {
            response.add("You need to log in to add items to your cart");
            return ResponseEntity.badRequest().body(response);
        } else {
            // if yes, get the user
            User user = userService.findById(Long.valueOf(userId));
            Product product = service.findById(Long.valueOf(id));
            Integer qtyUserSent = cart.getQuantity();
            var res = orderService.addItemToTheCart(product, qtyUserSent, cart, user);
            System.out.println(res);
            if(res.size()==1) {
                return ResponseEntity.badRequest().body(res);
            } else {
                return ResponseEntity.ok().body(res);
            }
        }
    }

    @PostMapping("/cart/update-cart/{orderId}")
    public ResponseEntity<Object> updateCart(@PathVariable String orderId, @RequestBody Map<String, String> obj) {
        String quantity =  obj.get("quantity");
        String productId = obj.get("productId");
        Long productIdLong = Long.valueOf(productId);
        var order = orderService.findById(Long.valueOf(orderId));
        var res = orderService.updateCart(order, productIdLong, quantity);

        System.out.println("ORDER ID UPDATE-CART: "+orderId);
        return ResponseEntity.ok().body(res);

    }
}
