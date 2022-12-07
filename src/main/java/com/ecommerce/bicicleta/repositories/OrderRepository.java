package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
