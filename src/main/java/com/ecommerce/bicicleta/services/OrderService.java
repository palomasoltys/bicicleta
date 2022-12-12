package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.dtos.OrderDto;
import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.repositories.OrderRepository;
import com.ecommerce.bicicleta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.get();
    }

    public List<OrderDto> findAllOrdersByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            List<Order> orderList = orderRepository.findAllByUserEquals(userOptional.get());
            return orderList.stream().map(OrderDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
