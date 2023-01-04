package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.services.LoginResponse;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<List<String>> userLogin(@RequestBody UserDto userDto, HttpServletResponse httpResponse, HttpSession session){

        LoginResponse response = service.userLogin(userDto);
        if (response.isSuccessful()) {
            session.setAttribute("user-id", response.getResponse().get(1));
            System.out.println(session.getAttribute("user-id"));
//            Cookie cookie = new Cookie("user-id", response.getResponse().get(1));
//            cookie.setMaxAge(7 * 24 * 60 * 60);
//            httpResponse.addCookie(cookie);
            return ResponseEntity.ok().body(response.getResponse());
        } else {
            return ResponseEntity.badRequest().body(response.getResponse());
        }

    }
    @PostMapping("/register-form")
    public ResponseEntity<List<String>> registerUser(@RequestBody UserDto userDto) {
        System.out.println(userDto);
        var response = service.addUser(userDto);
        System.out.println(response);
        return ResponseEntity.ok().body(response);
    }

}
