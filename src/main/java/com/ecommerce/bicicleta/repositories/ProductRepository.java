package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
