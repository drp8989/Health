package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import com.JustHealth.Health.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/{id}")
    private Product getProductById(@PathVariable Long id) throws ProductNotFoundException {

        return productService.findProductById(id);

    }
}
