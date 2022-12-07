package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll() {
        User u = new User(1L, "paloma@gmail.com", "paloma", "Paloma");
        return ResponseEntity.ok().body(u);
    }

}
