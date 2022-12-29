package com.ecommerce.bicicleta.services;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    boolean isSuccessful;
    List<String> response;


}
