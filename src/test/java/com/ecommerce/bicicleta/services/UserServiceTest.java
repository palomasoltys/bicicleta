package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void userLogin() {
        //Set up data
        User user = new User(1L, "test@test.com", "password", "test");
        UserDto userDto = new UserDto(user);

        //Define the behavior of the mock repository when its called by the userLogin method
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        //Call the userLogin method with the UserDto object
        LoginResponse loginResponse = userService.userLogin(userDto);

        // Verify that the login was successfully and the response message is correct
        assertThat(loginResponse.isSuccessful()).isTrue();
        assertThat(loginResponse.getResponse()).containsExactly("http://localhost:8080/", "1");

    }
}