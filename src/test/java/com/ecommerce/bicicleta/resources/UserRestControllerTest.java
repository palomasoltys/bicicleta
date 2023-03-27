package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.LoginResponse;
import com.ecommerce.bicicleta.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void userLogin() throws Exception {
//
//        //Set up test data
//        User user = new User(null, "test@test.com", "password", "test");
//        UserDto userDto = new UserDto(user);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setSuccessful(true);
//        loginResponse.setResponse("Login");
//        when(userService.userLogin(userDto)).thenReturn(user);
//
//
//        RequestBuilder request = MockMvcRequestBuilders.post("/login-form");
//        MvcResult result = mvc.perform(request).andReturn();
//        assertEquals("return here", result.getResponse().getContentAsString());
    }

    @Test
    void registerUser() {
    }
}