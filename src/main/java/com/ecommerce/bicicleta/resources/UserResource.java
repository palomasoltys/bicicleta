package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.UserService;
import com.sun.net.httpserver.HttpContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @GetMapping("/register")
    public String register(){
//        UserDto userDto = new UserDto();
//        model.addAttribute("user", userDto);
        return "register";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user-id", null);

        return "redirect:/users/login";
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<User> findById(@PathVariable Long id) {
//        User obj = service.findById(id);
//        return ResponseEntity.ok().body(obj);
//    }

    @GetMapping(value = "/{id}")
    public String getOrdersByUserId(@PathVariable Long id, Model model) {
        User user = service.findById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

}
