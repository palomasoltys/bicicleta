package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}