package com.ecommerce.bicicleta.dtos;

import com.ecommerce.bicicleta.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto implements Serializable {

    private Long id;
    private Instant moment;
    private OrderDto orderDto;

    public PaymentDto(Payment payment){
        if(payment.getId() != null) {
            this.id = payment.getId();
        }
        if(payment.getMoment() != null) {
            this.moment = payment.getMoment();
        }
        if(payment.getOrder() != null) {
            OrderDto order = new OrderDto(payment.getOrder());
            this.orderDto = order;
        }
    }
}
