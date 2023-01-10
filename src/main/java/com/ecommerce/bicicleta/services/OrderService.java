package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.dtos.OrderDto;
import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.OrderItem;
import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import com.ecommerce.bicicleta.repositories.OrderItemRepository;
import com.ecommerce.bicicleta.repositories.OrderRepository;
import com.ecommerce.bicicleta.repositories.ProductRepository;
import com.ecommerce.bicicleta.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.get();
    }

    @Transactional
    public List<String> addItemToTheCart(Product product, int quantity, OrderItem cart, User user) {
        List<String> response = new ArrayList<>();
        // get the current datetime
        Instant dateCreated = java.time.Clock.systemUTC().instant();
        // instantiate a new Order with the user and the datetime, and set the status to waiting payment
        OrderStatus orderStatus = OrderStatus.WAITING_PAYMENT;

        Order order = new Order(null, dateCreated, orderStatus, user);
        // then compare the quantity the user sent with the units in stock
        Integer qtyUserSent = cart.getQuantity();
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
//            return ResponseEntity.badRequest().body(response);
        }

        orderRepository.saveAndFlush(order);
        productRepository.saveAndFlush(product);
        return response;
    }

    @Transactional
    public List<String> updateCart(Order order, Long productId, String quantity) {
        List<String> response = new ArrayList<>();
        var items = order.getItems();
        for(OrderItem item : items) {
            var itemId = item.getProduct().getId();
            if(itemId == productId) {
                item.setQuantity(Integer.valueOf(quantity));
                System.out.println("QUANTITY SET QUANTITY: "+item.getQuantity().toString());
                response.add(item.getQuantity().toString());
                response.add(item.getSubTotal().toString());
                orderItemRepository.saveAndFlush(item);
            }
        }
        response.add(order.getTotal().toString());
        return response;
    }

    public List<Order> findAllOrdersByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            List<Order> orderList = orderRepository.findAllByUserEquals(userOptional.get());

            return orderList.stream().map(Order::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
