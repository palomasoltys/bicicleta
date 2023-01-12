package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
