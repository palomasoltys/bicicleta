package com.ecommerce.bicicleta.resources;

import com.ecommerce.bicicleta.entities.Product;
import com.ecommerce.bicicleta.entities.User;
import com.ecommerce.bicicleta.services.OrderService;
import com.ecommerce.bicicleta.services.ProductService;
import com.ecommerce.bicicleta.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class ProductResource {

    @Autowired
    private ProductService service;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

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

    @GetMapping()
    public String listAll(@RequestParam(name = "sort-order", required = false) String sortOrder, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user-id");
        if(userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            var openCart = orderService.findOpenCart(user);
            if(openCart != null) {
                model.addAttribute("cartSize", openCart.getItems().size());
            } else {
                model.addAttribute("cartSize", 0);

            }
            model.addAttribute("user", user);
        }

        if(sortOrder == null) {
            model.addAttribute("listProducts", service.findAll());
        }
         else if(sortOrder.equals("asc")) {
            model.addAttribute("listProducts", service.findAllByOrderByPriceAsc());
        } else {
            model.addAttribute("listProducts", service.findAllByOrderByPriceDesc());
        }

        return "home";
    }


    @GetMapping(value = "/products/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user-id");
        if(userId == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            var openCart = orderService.findOpenCart(user);
            if(openCart != null) {
                model.addAttribute("cartSize", openCart.getItems().size());
            } else {
                model.addAttribute("cartSize", 0);

            }
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
            var openCart = orderService.findOpenCart(user);
            if(openCart != null) {
                model.addAttribute("cartSize", openCart.getItems().size());
            } else {
                model.addAttribute("cartSize", 0);

            }
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
