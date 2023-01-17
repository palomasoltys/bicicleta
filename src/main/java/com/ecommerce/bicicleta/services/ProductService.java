package com.ecommerce.bicicleta.services;

import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Product> findByCategory(String category, String sortOrder) {
        List<Product> obj = productRepository.findAll();
        List<Product> objByCategory = new ArrayList<>();
        for(Product o : obj) {
            if(o.getCategory().equalsIgnoreCase(category)) {
                objByCategory.add(o);
            }
        }
        if (sortOrder == null || sortOrder.equals("-") ) {
            return objByCategory;
        } else if (sortOrder.equals("asc")) {
            Collections.sort(objByCategory, Comparator.comparingDouble(Product::getPrice));
        } else {
            Collections.sort(objByCategory, Comparator.comparingDouble(Product::getPrice).reversed());
        }
        for(var x: objByCategory) {
            System.out.println(x.getPrice());
        }
        return objByCategory;
    }

    public List<Product> findAllByOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> findAllByOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }
}
