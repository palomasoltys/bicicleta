package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.LoginResponse;
import com.ecommerce.bicicleta.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;




import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void userLogin() throws Exception {

        //Set up test data
        User user = new User(1L, "test@test.com", "password", "test");
        UserDto userDto = new UserDto(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccessful(true);
        loginResponse.setResponse(List.of("http://localhost:8080/", "1"));
        when(userService.userLogin(userDto)).thenReturn(loginResponse);

        //Serialize the userDto object to JSON Format
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDto);

        //Perform a POST request to the /login-form endpoint with the serialized UserDto object as the request body
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/api/users/login-form")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("http://localhost:8080")))
                .andReturn();

        //Verify the response from the server
        String response = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        String url = jsonNode.get(0).asText();
        assertThat(url).isEqualTo("http://localhost:8080/");
    }

}