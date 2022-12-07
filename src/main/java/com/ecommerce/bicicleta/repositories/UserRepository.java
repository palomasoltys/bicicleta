package com.ecommerce.bicicleta.repositories;

import com.ecommerce.bicicleta.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
