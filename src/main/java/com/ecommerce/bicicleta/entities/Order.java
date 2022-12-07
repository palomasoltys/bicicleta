package com.ecommerce.bicicleta.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderTrackingNumber;

    @Column
    private Integer totalQuantity;

    @Column
    private Double totalPrice;

    @Column
    private String status;

    @Column
    @CreatedDate
    private Instant dateCreated;

    @Column
    @LastModifiedDate
    private Instant lastUpdated;

    @ManyToOne
    @JsonBackReference
    private User user;

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