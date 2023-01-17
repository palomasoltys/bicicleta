package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.Address;
import com.ecommerce.bicicleta.entities.Payment;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.EmailSenderService;
import com.ecommerce.bicicleta.services.OrderService;
import com.ecommerce.bicicleta.services.ProductService;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.mail.MessagingException;
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

    @Autowired
    private EmailSenderService emailService;


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

    @PostMapping("/cart/checkout/submit/payment/{orderId}")
    public ResponseEntity<List<String>> addPaymentToOrder(@PathVariable String orderId) throws MessagingException {
        List<String> response = new ArrayList<>();
        var order = orderService.findById(Long.valueOf(orderId));
        Payment payment = new Payment();
        var moment = java.time.Clock.systemUTC().instant();
        payment.setMoment(moment);
        payment.setOrder(order);
        orderService.savePayment(payment, order);
        response.add("Payment added successfully");
        System.out.println(response.toString());
        var userEmail = order.getUser().getEmail();
        var userName = order.getUser().getName();
        var summary = new ArrayList<>();
        for(var x : order.getItems()){
            summary.add(x.getProduct().getName());
            summary.add(x.getProduct().getPrice());
            summary.add(x.getQuantity());
            summary.add(x.getSubTotal());
            summary.add(x.getOrder().getTotal());
        }
        String subject = "We've received your order. #"+orderId;
        String body = "<h1>"+userName+", just letting you know we've got your order.</h1><br> <h2>We'll send you an confirmation as soon as we send it.</h2><br> " +
                "<h2>Thank you for shopping with us.</h2><br>" +
                "<h2>Here is your summary:</h2><br> "+summary.toString();
        emailService.sendHtmlEmail(userEmail,subject, body);

        return ResponseEntity.ok().body(response);
    }
}
