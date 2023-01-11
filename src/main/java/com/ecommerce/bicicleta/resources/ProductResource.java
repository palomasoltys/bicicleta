package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.ProductService;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class ProductResource {

    @Autowired
    private ProductService service;

    @Autowired
    private UserService userService;


//    @RequestMapping
//    public String getHomePage()
//    {
//        return "home";
//    }

//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> findAll() {
//        List<Product> list = service.findAll();
//        return ResponseEntity.ok().body(list);
//    }

    @GetMapping
    public String listAll(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user-id");
        if(userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            model.addAttribute("user", user);
        }
        List<Product> listProducts = service.findAll();
        model.addAttribute("listProducts", listProducts);
        return "home";
    }


    @GetMapping(value = "/products/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user-id");
        if(userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            model.addAttribute("user", user);
        }
        Product product = service.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping(value = "/products/category/{category}")
    public String findByCategory(@PathVariable String category, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user-id");
        if(userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            model.addAttribute("user", user);
        }
        category = category.replaceAll("-", " ");
        List<Product> product = service.findByCategory(category);
        if(product.isEmpty()) {
            model.addAttribute("products", "NoData");
        } else {
            model.addAttribute("products", product);

        }
        return "category";
    }

    //    @GetMapping(value = "/products/{id}")
//    public ResponseEntity<Product> findById(@PathVariable Long id) {
//        Product obj = service.findById(id);
//        return ResponseEntity.ok().body(obj);
//    }

}
