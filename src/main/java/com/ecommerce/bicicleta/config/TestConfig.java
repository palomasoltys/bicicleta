package com.ecommerce.bicicleta.config;

import com.ecommerce.bicicleta.entities.*;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import com.ecommerce.bicicleta.repositories.OrderItemRepository;
import com.ecommerce.bicicleta.repositories.OrderRepository;
import com.ecommerce.bicicleta.repositories.ProductRepository;
import com.ecommerce.bicicleta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {


        Product p1 = new Product(null, "2022 Polygon Xtrada 5 Hardtail Mountain Bike",
                "ALX CX Sport 6061 Aluminum Frame, Modern XC Geometry, Suntour XCM, 120mm Travel Fork, Shimano Deore M4100 2x10 Speed, Tektro Hydraulic Disc Brakes",
                "Mountain Bike", 599.00, "https://www.bikesonline.com/assets/full/XTR4D4_5_22.jpg?20211004232123",
                40, Instant.parse("2022-11-20T10:53:07Z"), Instant.parse("2022-11-20T11:12:07Z"));
        Product p2 = new Product(null, "2022 Polygon Xtrada 7 Hardtail Mountain Bike",
                "ALX CX Sport 6061 Aluminum Frame, Modern XC Geometry, Suntour XCR 32 Air, 120mm Travel Fork, Shimano Deore M6100 1x12 Speed, Shimano Hydraulic Disc Brakes",
                "Mountain Bike", 849.00, "https://www.bikesonline.com/assets/full/XTRADA_7_22.jpg?20211004235448",
                30, Instant.parse("2022-11-20T10:58:07Z"), Instant.parse("2022-11-20T10:58:07Z"));
        Product p3 = new Product(null, "2022 Polygon Gili Velo - Urban eBike",
                "20-inch wheels zippy Urban eBike Aluminum frame, Bafang 250W motor with 252Wh battery, Tektro hydraulic disc brakes, Weight 38.36lbs",
                "E-Bike", 1349.00, "https://www.bikesonline.com/assets/full/GILIVELO_2021.png?20220807190138",
                6, Instant.parse("2022-12-02T01:21:22Z"), Instant.parse("2022-12-02T01:21:22Z"));

        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        User u1 = new User(null, "paloma@gmail.com", "123456", "Paloma");
        User u2 = new User(null, "nick@gmail.com", "654321", "Nick");

        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p2, 2, p2.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null,Instant.parse("2019-06-20T21:53:07Z"),o1);
        o1.setPayment(pay1);

        orderRepository.save(o1);

    }
}
