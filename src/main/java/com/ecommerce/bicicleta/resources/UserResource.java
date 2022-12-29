package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.UserService;
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

//    @PostMapping(value = "/register-form", consumes = {"application/xml","application/json"})
//    public List<String> addUser(@RequestBody UserDto userDto){
//        String passHash = passwordEncoder.encode(userDto.getPassword());
//        userDto.setPassword(passHash);
//        System.out.println(userDto.toString());
//        return service.addUser(userDto);
//    }

    @PostMapping("/register-form")
    public String registerUser(UserDto userDto) {
        System.out.println( userDto.toString());
        return "redirect:/users/login";
    }

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

    @PostMapping("/login-form")
    public List<String> userLogin(@RequestBody UserDto userDto){
        return service.userLogin(userDto);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
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
