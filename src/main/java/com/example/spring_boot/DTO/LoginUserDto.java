package com.example.spring_boot.DTO;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    
    private String password;
    
    // getters and setters here...
}