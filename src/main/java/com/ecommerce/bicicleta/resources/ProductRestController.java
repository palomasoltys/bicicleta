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

            // get the current datetime
            Instant dateCreated = java.time.Clock.systemUTC().instant();
            // instantiate a new Order with the user and the datetime, and set the status to waiting payment
            OrderStatus orderStatus = OrderStatus.WAITING_PAYMENT;

            //transactional and save and flush
            Order order = new Order(null, dateCreated, orderStatus, user);
            // then compare the quantity the user sent with the units in stock
            Integer qtyUserSent = cart.getQuantity();
            Product product = service.findById(Long.valueOf(id));
            Integer unitsInStock = product.getUnitsInStock();
            //if less, subtract the quantity in the units in stock (database)
            if (qtyUserSent <= unitsInStock) {

                product.setUnitsInStock(unitsInStock - qtyUserSent);
                // and calculate the total of the cart (quantity * price)
                Double subTotal = cart.getSubTotal(product.getPrice(), qtyUserSent);
                response.add(Integer.toString(qtyUserSent));
                response.add(Double.toString(subTotal));
            } else {
                //if not, send a bad request and in the js send an alert saying "we only have x left in stock"
                response.add("We only have " + product.getUnitsInStock() + " left in stock");
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/cart/update-cart/{orderId}")
    public ResponseEntity<Object> updateCart(@PathVariable String orderId, @RequestBody Map<String, String> obj) {
        List<String> response = new ArrayList<>();
        String quantity =  obj.get("quantity");
        String price =  obj.get("price");
        String productId = obj.get("productId");
        Long productIdLong = Long.valueOf(productId);
        Order order = orderService.findById(Long.valueOf(orderId));
        var items = order.getItems();
        for(OrderItem item : items) {
            var itemId = item.getProduct().getId();
            if(itemId == productIdLong) {
                item.setQuantity(Integer.valueOf(quantity));
                System.out.println("QUANTITY WHILE SET QUANTITY: "+item.getQuantity().toString());
                response.add(item.getQuantity().toString());
                response.add(item.getSubTotal().toString());
            }
        }

        response.add(order.getTotal().toString());
        for(OrderItem item: items) {
            var itemId = item.getProduct().getId();
            if(itemId == productIdLong) {
                System.out.println("QUANTITY AFTER SET QUANTITY: "+item.getQuantity().toString());
            }

        }
        System.out.println("ORDER ID UPDATE-CART: "+orderId);
        return ResponseEntity.ok().body(response);

    }
}
