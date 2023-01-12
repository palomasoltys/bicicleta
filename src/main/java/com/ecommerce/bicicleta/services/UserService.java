package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.Address;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.repositories.AddressRepository;
import com.ecommerce.bicicleta.repositories.UserRepository;
import com.ecommerce.bicicleta.services.exceptions.DatabaseException;
import com.ecommerce.bicicleta.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("http://localhost:8080/users/login");
        return response;
    }

    @Transactional
    public Address saveAddress(Address address, User user) {
        address.setUser(user);
        return addressRepository.saveAndFlush(address);
    }
    public LoginResponse userLogin(UserDto userDto) {
        System.out.println(userDto);
        LoginResponse response = new LoginResponse();
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()) {
              if(userDto.getPassword().equals(userOptional.get().getPassword())) {
//            if(passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                response.setSuccessful(true);
                response.setResponse(List.of("http://localhost:8080/",String.valueOf(userOptional.get().getId())));
            } else {
                response.setSuccessful(false);
                response.setResponse(List.of("Email or password incorrect"));
            }
        } else {
            response.setSuccessful(false);
            response.setResponse(List.of("Email or password incorrect"));

        }
        System.out.println(response);
        return response;
    }

    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    public void delete(Long id){
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User obj) {
        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, obj);
            return userRepository.save(entity);
        } catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());

    }
}
