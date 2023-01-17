package com.ecommerce.bicicleta.resources;


import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;

import com.ecommerce.bicicleta.services.OrderService;
import com.ecommerce.bicicleta.services.ProductService;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<List<String>> addToCart(@RequestBody Product product, @RequestParam("quantity") Integer quantity, HttpSession session) {
        List<String> response = new ArrayList<>();
        // Get the current user
        String userId = (String) session.getAttribute("user-id");
        // if not, return a bad request and redirect to the login page with an alert
        if (userId == null) {
            response.add("You need to log in to add items to your cart");
            return ResponseEntity.badRequest().body(response);
        } else {
            // if yes, get the user
            User user = userService.findById(Long.valueOf(userId));
            orderService.addToCart(user, product, quantity);
            response.add(quantity.toString());
            response.add("Item added to your cart");

            return ResponseEntity.ok().body(response);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<List<Product>> getAllProductsSortedByPriceAsc() {
        var products = service.findAllByOrderByPriceAsc();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/test/2")
    public ResponseEntity<List<Product>> getAllProductsSortedByPriceDesc() {
        var products = service.findAllByOrderByPriceDesc();
        return ResponseEntity.ok().body(products);
    }

//    @PostMapping("/cart/add-to-the-cart/{id}")
//    public ResponseEntity<List<String>> addToTheCart(@RequestBody OrderItem cart, @PathVariable String id, HttpSession session){
//        List<String> response = new ArrayList<>();
//        //verify if the user is logged in by comparing the session
//        String userId = (String) session.getAttribute("user-id");
//        System.out.println("User Id: "+userId);
//        // if not, return a bad request and redirect to the login page with an alert
//        if(userId == null) {
//            response.add("You need to log in to add items to your cart");
//            return ResponseEntity.badRequest().body(response);
//        } else {
//            // if yes, get the user
//            User user = userService.findById(Long.valueOf(userId));
//            System.out.println("CART::::: "+cart.getProduct().getName());
//            Long orderId = cart.getOrder().getId();
//            Product product = service.findById(Long.valueOf(id));
//            Integer qtyUserSent = cart.getQuantity();
//            var res = orderService.addItemToTheCart(product, qtyUserSent, cart, user, orderId);
//            System.out.println(res);
//            if(res.size()==1) {
//                return ResponseEntity.badRequest().body(res);
//            } else {
//                return ResponseEntity.ok().body(res);
//            }
//        }
//    }

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

    @GetMapping("/cart/checkout/{orderId}")
    public ResponseEntity<Object> checkoutCart(@PathVariable String orderId) {
        System.out.println("HERE");
        List<String> res = new ArrayList<>();
        var orders = orderService.findById(Long.valueOf(orderId));
        var items = orders.getItems();
        for(var item: items) {
            res.add(item.getQuantity().toString());
            res.add(item.getProduct().getPrice().toString());
            res.add(item.getSubTotal().toString());
        res.add(orders.getTotal().toString());
        }
        System.out.println(res);
        return ResponseEntity.ok().body(res);
    }
}
