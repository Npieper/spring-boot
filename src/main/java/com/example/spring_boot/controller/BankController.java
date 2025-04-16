package com.example.spring_boot.controller;

import com.example.spring_boot.entity.BankAccount;
import com.example.spring_boot.entity.LoginResponse;
import com.example.spring_boot.entity.Product;
import com.example.spring_boot.entity.User;
import com.example.spring_boot.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/accounts")
    public ResponseEntity<BankAccount> createAccount(@RequestParam("firstName") String firstName,
                                                     @RequestParam("lastName")String lastName) {

        return ResponseEntity.ok(bankService.createBankAccount(firstName, lastName));

    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<List<Product>> topUpBalance(@PathVariable(value = "id") Integer id, @RequestBody double amount ) {

        System.out.println("Im in topUpBalance for account  " + id + "with amount " + amount);
        bankService.topUpBalance(id, amount);
        /*List<Product> filteredAndSortedProducts = products.stream()
                .filter(product -> product.getPrice() > 50)
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
        return ResponseEntity.ok(filteredAndSortedProducts);*/
        return null;
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<List<Product>> showBalance(@RequestBody List<Product> products) {

        /*List<Product> filteredAndSortedProducts = products.stream()
                .filter(product -> product.getPrice() > 50)
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
        return ResponseEntity.ok(filteredAndSortedProducts);*/
        return null;
    }


    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<List<Product>> withdrawMoney(@RequestBody List<Product> products) {

        /*List<Product> filteredAndSortedProducts = products.stream()
                .filter(product -> product.getPrice() > 50)
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
        return ResponseEntity.ok(filteredAndSortedProducts);*/
        return null;
    }


}
