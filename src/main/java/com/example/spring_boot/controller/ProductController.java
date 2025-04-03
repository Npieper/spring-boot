package com.example.spring_boot.controller;

import com.example.spring_boot.entity.LoginResponse;
import com.example.spring_boot.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class ProductController {
    @PostMapping("/products")
    public ResponseEntity<List<Product>> insertProducts(@RequestBody List<Product> products) {

        List<Product> filteredAndSortedProducts = products.stream()
                .filter(product -> product.getPrice() > 50)
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
        return ResponseEntity.ok(filteredAndSortedProducts);
    }
}
