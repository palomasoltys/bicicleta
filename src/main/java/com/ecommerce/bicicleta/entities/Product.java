package com.ecommerce.bicicleta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private Double price;

    @Column
    private String imgUrl;

    @Column
    private Integer unitsInStock;

    @Column
    @CreatedDate
    private Instant dateCreated;

    @Column
    @LastModifiedDate
    private Instant lastUpdated;

    @OneToMany(mappedBy = "id.product")
//    @JsonIgnore
    private Set<OrderItem> items = new HashSet<>();


    public Product(Long id, String name, String description, String category, Double price, String imgUrl, Integer unitsInStock, Instant dateCreated, Instant lastUpdated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imgUrl = imgUrl;
        this.unitsInStock = unitsInStock;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Product() {
    }


    public Set<Order> getOrders() {
        Set<Order> set = new HashSet<>();
        for(OrderItem x : items) {
            set.add(x.getOrder());
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

