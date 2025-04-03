package com.example.spring_boot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String name;
    private String category;
    private double price;

}
