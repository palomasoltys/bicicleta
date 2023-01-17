package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Order> hasOrderStatus(OrderStatus status) {
        return (root, query, cb) -> cb.equal(root.get("orderStatus"), status.getCode());
    }
    public static Specification<Order> hasUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }
}


