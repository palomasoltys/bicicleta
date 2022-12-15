package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.repositories.ProductRepository;
import com.ecommerce.bicicleta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        return obj.get();
    }

    public List<Product> findByCategory(String category) {
        List<Product> obj = productRepository.findAll();
        List<Product> objByCategory = new ArrayList<>();
        for(Product o : obj) {
            if(o.getCategory().equalsIgnoreCase(category)) {
                objByCategory.add(o);
            }
        }
        return objByCategory;
    }
}
