package com.ecommerce.bicicleta.entities;

import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
//    private String orderTrackingNumber;

//    @Column
//    private Integer totalQuantity;
//
//    @Column
//    private Double totalPrice;

    @Column
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant dateCreated;

    @Column
    private Integer orderStatus;

//    @Column
//    @LastModifiedDate
//    private Instant lastUpdated;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    public Order(Long id, Instant dateCreated, OrderStatus orderStatus, User user) {
        this.id = id;
        this.dateCreated = dateCreated;
        setOrderStatus(orderStatus);
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if(orderStatus != null) {
            this.orderStatus = orderStatus.getCode();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}