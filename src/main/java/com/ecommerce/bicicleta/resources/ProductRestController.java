package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.dtos.UserDto;
import com.ecommerce.bicicleta.entities.OrderItem;
import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping("/cart/add-to-the-cart/{id}")
    public ResponseEntity<List<String>> addToTheCart(@RequestBody OrderItem cart, @PathVariable String id){
        List<String> response = new ArrayList<>();
        //verify if the user is logged in by comparing the cookie
            //if not, return a bad request and redirect to the login page with an alert
            //if yes, compare the quantity the user sent with the units in stock
            Integer qtyUserSent = cart.getQuantity();
            Product product = service.findById(Long.valueOf(id));
            Integer unitsInStock = product.getUnitsInStock();
        //if less, subtract the quantity in the units in stock (database)
        if(qtyUserSent <= unitsInStock) {
                product.setUnitsInStock(unitsInStock - qtyUserSent);
            // and calculate the total of the cart (quantity * price)
                Double subTotal = cart.getSubTotal(product.getPrice(), qtyUserSent);
                response.add(Double.toString(subTotal));
                System.out.println(subTotal);
            } else {
            //if not, send a bad request and in the js send an alert saying "we only have x left in stock"
            response.add("We only have "+product.getUnitsInStock()+" left in stock");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().body(response);

    }
}
