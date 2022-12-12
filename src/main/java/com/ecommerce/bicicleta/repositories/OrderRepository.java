package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserEquals(User user);
}
