package com.ecommerce.bicicleta.dtos;

import com.ecommerce.bicicleta.entities.Order;
import com.ecommerce.bicicleta.entities.OrderItem;
import com.ecommerce.bicicleta.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private Long id;
    private Instant dateCreated;
    private Integer orderStatus;
    private UserDto userDto;
    private Set<OrderItem> items = new HashSet<>(); // Order Item needs a DTO?
    private Payment payment; // Payment needs a DTO?

    public OrderDto(Order order) {
        if(order.getId() != null) {
            this.id = order.getId();
        }
        if(order.getDateCreated() != null) {
            this.dateCreated = order.getDateCreated();
        }
        // Do I need to do this with all private fields?
    }


}
