package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.Address;
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

@RestController
@RequestMapping(value = "/orders")
public class OrderRestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;



    @PostMapping("/cart/checkout/submit/{orderId}")
    public ResponseEntity<List<String>> addAddressToUser(@RequestBody Address address, HttpSession session) {
        List<String> response = new ArrayList<>();
        String userId = (String) session.getAttribute("user-id");
        User user = userService.findById(Long.valueOf(userId));
        userService.saveAddress(address, user);
        response.add("Address added successfully");
        System.out.println(response.toString());
            return ResponseEntity.ok().body(response);
        }
}
