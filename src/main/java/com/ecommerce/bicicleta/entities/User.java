package com.ecommerce.bicicleta.entities;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
    private List<Order> orderList = new ArrayList<>();

    public User() {
    }

    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(UserDto userDto) {
        if(userDto.getEmail() != null) {
            this.email = userDto.getEmail();
        }
        if(userDto.getPassword() != null) {
            this.password = userDto.getPassword();
        }
    }

    public List<Order> getOrders() {
        return orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

