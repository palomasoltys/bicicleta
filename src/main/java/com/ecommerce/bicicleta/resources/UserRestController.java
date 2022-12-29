package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.services.LoginResponse;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login-form")
    public ResponseEntity<List<String>> userLogin(@RequestBody UserDto userDto){

        LoginResponse response = service.userLogin(userDto);
        if (response.isSuccessful()) {
            return ResponseEntity.ok().body(response.getResponse());
        } else {
            return ResponseEntity.badRequest().body(response.getResponse());
        }

    }
}
