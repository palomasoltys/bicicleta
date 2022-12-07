package com.ecommerce.bicicleta.config;

import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "paloma@gmail.com", "123456", "Paloma");
        User u2 = new User(null, "nick@gmail.com", "654321", "Nick");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
