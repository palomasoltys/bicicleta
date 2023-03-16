package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.entities.*;
import com.ecommerce.bicicleta.entities.enums.OrderStatus;
import com.ecommerce.bicicleta.repositories.*;
import jakarta.transaction.Transactional;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Order order;
    public void setOrder(Order order){
        this.order=order;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.get();
    }

//    public OrderItem findOrderItemById(Long productId, Long orderId) {
//        Optional<OrderItem> obj = orderItemRepository.findById(orderId);
//        Optional<Product> product = productRepository.findById(productId);
//
//        return obj.get();
//    }

    @Transactional
    public Payment savePayment(Payment payment, Order order) {
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.saveAndFlush(order);
        return paymentRepository.saveAndFlush(payment);
    }


    public List<Order> paidOrders(User user) {
        List<Order> orders = user.getOrders();
        List<Order> paidOrders = new ArrayList<>();
        for(var order:orders){
            if(order.getOrderStatus().getCode() > 1) {
                paidOrders.add(order);
            }
        }
        return paidOrders;
    }
    public Order findOpenCart(User user) {
        // Check if the user already has an open cart
        Order openCart = orderRepository.findFirstByUserAndOrderStatus(user, OrderStatus.WAITING_PAYMENT.getCode());
        return openCart;
    }

    @Transactional
    public void addToCart(User user, Product product, int quantity) {
        // Check if the user already has an open cart
        Order openCart = orderRepository.findFirstByUserAndOrderStatus(user, OrderStatus.WAITING_PAYMENT.getCode());
        if (openCart != null) {
//           var order = orderRepository.findById(openCart.getId());
            Optional<Order> order = orderRepository.findOne(Specification.where(OrderSpecification.hasOrderStatus(OrderStatus.WAITING_PAYMENT)).and(OrderSpecification.hasUser(user)));
            // Add the product and quantity to the existing cart
            OrderItem orderItem = new OrderItem(order.get(),product, quantity,product.getPrice());
            orderItemRepository.saveAndFlush(orderItem);
            openCart.getItems().add(orderItem);
            orderRepository.saveAndFlush(openCart);
        } else {
            // Create a new cart for the user
            Order newCart = new Order();
            newCart.setDateCreated(java.time.Clock.systemUTC().instant());
            newCart.setUser(user);
            newCart.setOrderStatus(OrderStatus.WAITING_PAYMENT);

            // Add the product and quantity to the new cart
            OrderItem orderItem = new OrderItem(newCart, product, quantity, product.getPrice());
            Set<OrderItem> items = new HashSet<>();
            items.add(orderItem);
            for(var item : items) {
                System.out.println("ADD TO CART ORDER SERVICE: "+item.getProduct().getName());
            }
            newCart.setItems(items);
            // persist the new cart in the database
            orderRepository.saveAndFlush(newCart);
            orderItemRepository.saveAndFlush(orderItem);

        }
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void removeExpiredItems() {
        Instant now = Instant.now();
        List<Order> orders = orderRepository.findByOrderStatus(1);
        if(!orders.isEmpty()){
            for(int i=0; i<orders.size(); i++) {
                Order order = orders.get(i);
                System.out.println("Order found: " + order.getId());
                for (Iterator<OrderItem> iterator = order.getItems().iterator(); iterator.hasNext(); ) {
                    OrderItem item = iterator.next();
                    Duration duration = Duration.between(order.getDateCreated(), now);
                    if(i == orders.size() - 1 && duration.toHours() > 24) {
                        order.setUser(null);
                        orderItemRepository.delete(item);
                        orderRepository.delete(order);
                    }
                    if (duration.toHours() > 24) {
                        System.out.println("Removing item: " + item.getProduct().getName());
                        iterator.remove();
                    }
                }
            }
            if (order != null && !order.getItems().isEmpty()) {
                orderRepository.saveAndFlush(order);
            } else if (order != null) {
                orderRepository.delete(order);
            }
        } else {
            System.out.println("No order found or order status is not valid");
        }
    }



    @Transactional
    public List<String> addItemToTheCart(Product product, int quantity, OrderItem cart, User user, Long orderId) {
        Optional<Order> orderObj = orderRepository.findById(orderId);
        System.out.println(orderObj.isPresent());

        List<String> response = new ArrayList<>();

        // then compare the quantity the user sent with the units in stock
        Integer qtyUserSent = cart.getQuantity();
        Integer unitsInStock = product.getUnitsInStock();
        //if less, subtract the quantity in the units in stock (database)
        if (qtyUserSent <= unitsInStock) {

            product.setUnitsInStock(unitsInStock - qtyUserSent);
            // and calculate the total of the cart (quantity * price)
            Double subTotal = cart.getSubTotal(product.getPrice(), qtyUserSent);
            response.add(Integer.toString(qtyUserSent));
            response.add(Double.toString(subTotal));
        } else {
            //if not, send a bad request and in the js send an alert saying "we only have x left in stock"
            response.add("We only have " + product.getUnitsInStock() + " left in stock");
        }

        Set<OrderItem> items = new HashSet<>();
        items.add(cart);

        if(!orderObj.isPresent()) {
            // get the current datetime
            Instant dateCreated = java.time.Clock.systemUTC().instant();
            // instantiate a new Order with the user and the datetime, and set the status to waiting payment
            OrderStatus orderStatus = OrderStatus.WAITING_PAYMENT;
            Order order = new Order(null, dateCreated, orderStatus, user);

            order.setItems(items);
            orderRepository.saveAndFlush(order);

        } else {
            Order order = orderObj.get();

            order.setItems(items);
            orderRepository.saveAndFlush(order);
        }

        productRepository.saveAndFlush(product);
        return response;
    }

    @Transactional
    public List<String> updateCart(Order order, Long productId, String quantity) {
        List<String> response = new ArrayList<>();
        var items = order.getItems();
        for(OrderItem item : items) {
            var itemId = item.getProduct().getId();
            if(itemId == productId) {
                item.setQuantity(Integer.valueOf(quantity));
                System.out.println("QUANTITY SET QUANTITY: "+item.getQuantity().toString());
                response.add(item.getQuantity().toString());
                response.add(item.getSubTotal().toString());
                orderItemRepository.saveAndFlush(item);
            }
        }
        response.add(order.getTotal().toString());
        return response;
    }

    public List<Order> findAllOrdersByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            List<Order> orderList = orderRepository.findAllByUserEquals(userOptional.get());

            return orderList.stream().map(Order::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public void deleteOrderByOrderId(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public void deleteOrderItem(OrderItem item) {
        orderItemRepository.delete(item);
    }

}
