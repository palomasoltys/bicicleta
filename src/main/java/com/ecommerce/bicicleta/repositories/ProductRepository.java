package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
}
