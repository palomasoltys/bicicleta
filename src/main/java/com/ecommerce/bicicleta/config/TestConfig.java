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
                "EBike", 1349.00, "https://www.bikesonline.com/assets/full/GILIVELO_2021.png?20220807190138",
                6, Instant.parse("2022-12-02T01:21:22Z"), Instant.parse("2022-12-02T01:21:22Z"));
        Product p4 = new Product(null, "2022 Marin Lombard 1 - Urban & Gravel Bike",
                "Series 2 Lombard - 6061 Aluminum Frame, 6061 Aluminum - Straight-Blade Fork, Shimano Sora 2x9 Speed, Tekto Road Mechanical Disc, Vee Tire G-Sport 700x40c",
                "Gravel Bike", 999.00, "https://www.bikesonline.com/assets/full/4888019.jpg?20211111192110",
                6, Instant.parse("2022-12-20T01:21:22Z"), Instant.parse("2022-12-21T01:21:22Z"));
        Product p5 = new Product(null, "2022 Marin Nicasio - Steel Gravel Urban Bike",
                "Series 1 Double Butted Steel Beyond Road Platform, Disc Brakes, Fender and Rack Mounts, Shimano Claris 2x8 Speed Groupset, 700x30mm Tires",
                "Gravel Bike", 889.00, "https://www.bikesonline.com/assets/full/4888095.jpg?20210913090931",
                6, Instant.parse("2022-12-20T05:21:22Z"), Instant.parse("2022-12-21T06:21:22Z"));
        Product p6 = new Product(null, "2022 Marin Sausalito E2 - Urban E-Bike",
                "Series 3 Aluminum frame, Shimano Steps E6100 eBike system, 418 watt-hour removable battery, Shimano Deore 1x11 Drivetrain, 650b tires",
                "EBike", 2899.00, "https://www.bikesonline.com/assets/full/4928584.jpg?20211111191855",
                6, Instant.parse("2022-12-26T05:21:22Z"), Instant.parse("2022-12-26T07:21:22Z"));
        Product p7 = new Product(null, "2022 Polygon Cleo 2 - 27.5 inch Women's Mountain Bike",
                "Hydroformed AL6 Alutech Aluminum frame, Shimano Acera/Altus 24 Speed Drivetrain, 120mm Suspension Fork, 27.5\" Polygon Double Wall Rims, Tektro Hydraulic Disc Brakes",
                "Mountain Bike", 499.00, "https://www.bikesonline.com/assets/full/4875550.jpg?20210317031416",
                6, Instant.parse("2022-12-26T08:25:22Z"), Instant.parse("2022-12-26T08:25:22Z"));
        Product p8 = new Product(null, "2022 Polygon Cascade 4 - 27.5 inch Mountain Bike",
                "Alutech XC Sport 27.5 Aluminum, Suntour XCE 100mm Suspesion Fork, Shimano Altus / Toruney 24 Speed Drivetrain, Hydraulic Disc Brakes, 27.5\" Polygon Double Wall Rims",
                "Mountain Bike", 349.00, "https://www.bikesonline.com/assets/full/C4SC4DE_4_22.jpg?20210811233424",
                6, Instant.parse("2022-12-26T09:25:22Z"), Instant.parse("2022-12-26T09:28:22Z"));
        Product p9 = new Product(null, "2022 Marin Gestalt 1 - Gravel Bike",
                "Lightweight and durable Series 3 6061 alloy frame, tapered headtube, Lightweight carbon fork with thru axle, Shimano Sora 2x9 Speed, Tektro disc brakes for all-weather stopping power, WTB Exposure Comp 700x32 tires",
                "Gravel Bike", 1089.00, "https://www.bikesonline.com/assets/full/4829654.jpg?20210913090853",
                6, Instant.parse("2022-12-27T09:18:22Z"), Instant.parse("2022-12-27T09:32:22Z"));
        Product p10 = new Product(null, "2022 Marin Gestalt X10 - Gravel Bike",
                "Butted and Formed Series 3 6061 Aluminum Frame, Carbon Fork with Thru-Axle, MicroSHIFT Advent X 10-Speed Wide-Range Drivetrain, Mechnical Disc Brakes",
                "Gravel Bike", 1449.00, "https://www.bikesonline.com/assets/full/4914361.jpg?20210913091133",
                6, Instant.parse("2022-12-30T02:02:22Z"), Instant.parse("2022-12-30T02:02:22Z"));
        Product p11 = new Product(null, "Polygon Premier 5 - Grey/ Lemon - 27.5 inch Mountain Bike",
                "Hydroformed 6061 Alutech Aluminum Frame, Suntour XCM Suspension Fork with Lockout, Shimano Altus 2x9 Speed Drivetrain, Tektro Hydraulic Disc Brakes, 27.5\" or 29 Double Wall Rims with Shimano hubs",
                "Mountain Bike", 449.00, "https://www.bikesonline.com/assets/full/4875777.jpg?20210317031411",
                6, Instant.parse("2022-12-31T01:02:22Z"), Instant.parse("2022-12-31T03:05:12Z"));
        Product p12 = new Product(null, "2022 Polygon Xtrada 6 1x11 - Mountain Bike",
                "ALX XC Sport 6061 Aluminum Frame, Modern XC Geometry, Suntour XCR 32, 120mm Travel Fork, Shimano Deore M5100 1x11 Speed, Shimano Hydraulic Disc Brakes",
                "Mountain Bike", 719.00, "https://www.bikesonline.com/assets/full/2022_XTRADA_6.jpg?20211004221045",
                6, Instant.parse("2023-01-01T05:12:28Z"), Instant.parse("2023-01-01T05:12:28Z"));
        Product p13 = new Product(null, "2022 Marin Gestalt X11 - Gravel Bike",
                "Butted and Formed Series 3 6061 Aluminum Frame, Carbon Fork with Thru-Axle, Shimano GRX 11-Speed Wide Range Drivetrain, Hydraulic Disc Brakes",
                "Gravel Bike", 2349.00, "https://www.bikesonline.com/assets/full/4916950.jpg?20210913083319",
                6, Instant.parse("2023-01-01T05:30:28Z"), Instant.parse("2023-01-01T05:30:28Z"));
        Product p14 = new Product(null, "2022 Marin Sausalito E1 - Urban E-Bike",
                "Series 3 Aluminum frame, Shimano Steps E5000 eBike system, 418 watt-hour removable battery, Shimano Deore 1x10 Drivetrain, 650b tires",
                "EBike", 1999.00, "https://www.bikesonline.com/assets/full/4928582.jpg?20211111192058",
                6, Instant.parse("2023-01-01T05:35:28Z"), Instant.parse("2023-01-01T05:35:28Z"));
        Product p15 = new Product(null, "2022 Marin Wildcat Trail 1 - Women's Mountain Bike",
                "Series 1 6061 Aluminum, 27.5 Wheels, Suntour XCE Suspension Fork, Shimano 3x7 Speed Drivetrain, Power CX7 Mechanical Disc Brakes, Modern Trail Geometry with low Standover",
                "Mountain Bike", 499.00, "https://www.bikesonline.com/assets/full/2022_WILDCAT_1.jpg?20220615222050",
                37, Instant.parse("2023-01-02T05:35:10Z"), Instant.parse("2023-01-02T05:35:10Z"));
        Product p16 = new Product(null, "2022 Polygon Cascade 2 - 27.5 inch Mountain Bike",
                "Alutech XC Sport 27.5 Aluminum, Shimano Tourney 21 Speed Drivetrain, 100mm Suspension Fork, 27.5\" Polygon Double Wall Rims, Tektro Mechanical Disc Brakes",
                "Mountain Bike", 349.00, "https://www.bikesonline.com/assets/full/2022_CASCADE_2.jpg?20220516172529",
                18, Instant.parse("2023-01-02T08:30:10Z"), Instant.parse("2023-01-02T08:30:10Z"));
        Product p17 = new Product(null, "2022 Marin Gestalt 2.5 - Gravel Bike",
                "Lightweight and durable Series 3 6061 alloy frame, tapered headtube, Lightweight carbon fork with thru axle, Shimano Tiagra 2x10 Speed, Shimano Hydraulic Disc Brakes, Schwalbe G-One 700x30C tires",
                "Gravel Bike", 1699.00, "https://www.bikesonline.com/assets/full/2022_GESTALT_25.jpg?20211111192123",
                35, Instant.parse("2023-01-02T12:30:02Z"), Instant.parse("2023-01-02T12:30:02Z"));
        Product p18 = new Product(null, "2022 Marin Gestalt - Gravel Bike",
                "Lightweight and durable Series 2 6061 alloy frame, Aluminum Fork, Shimano Claris 2x8 Speed, Mechnical Disc Brakes, WTB Exposure Comp 700x32 tires",
                "Gravel Bike", 999.00, "https://www.bikesonline.com/assets/full/482965.jpg?20211111191812",
                30, Instant.parse("2023-01-03T12:30:02Z"), Instant.parse("2023-01-03T12:35:02Z"));
        Product p19 = new Product(null, "2022 Marin Bolinas Ridge 2 - Mountain Bike",
                "Series 1 6061 Aluminum, 27.5\"/29 Wheels, Suntour XCM Suspension Fork, Shimano 3x8 Speed Drivetrain, Tektro Hydraulic Disc Brakes, Modern Trail Geometry with Standover",
                "Mountain Bike", 449.00, "https://www.bikesonline.com/assets/full/2022_BOLINAS_RIDGE_2.jpg?20220615220848",
                3, Instant.parse("2023-01-03T12:57:28Z"), Instant.parse("2023-01-03T12:57:28Z"));


        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19));

        User u1 = new User(null, "palomasoltys@gmail.com", "123456", "Paloma");
        User u2 = new User(null, "nick@gmail.com", "654321", "Nick");
        User u3 = new User(null, "bal@gmail.com", "123", "Bal");

        Order o1 = new Order(null, Instant.parse("2023-02-15T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u3);

        userRepository.saveAll(Arrays.asList(u1, u2, u3));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        OrderItem oi1 = new OrderItem(o1, p8, 1, p8.getPrice());
        OrderItem oi2 = new OrderItem(o2, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p2, 2, p2.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null,Instant.parse("2019-06-20T21:53:07Z"),o1);
        o1.setPayment(pay1);

        orderRepository.save(o1);

    }
}
