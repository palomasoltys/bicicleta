package com.ecommerce.bicicleta.dtos;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto implements Serializable {

    private Long id;
    private String email;
    private String password;
    private String name;
    private List<Order> orderDtoList = new ArrayList<>();


    public UserDto(User user) {
        if(user.getId() != null) {
            this.id = user.getId();
        }
        if(user.getEmail() != null) {
            this.email = user.getEmail();
        }
        if(user.getPassword() != null) {
            this.password = user.getPassword();
        }
    }

}
