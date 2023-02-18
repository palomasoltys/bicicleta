package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Order findFirstByUserAndOrderStatus(User user, Integer orderStatus);
    List<Order> findAllByUserEquals(User user);

    List<Order> findByOrderStatus(Integer orderStatus);
}
