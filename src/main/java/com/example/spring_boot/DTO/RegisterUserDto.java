package com.example.spring_boot.DTO;

import lombok.*;

@Setter
@Getter
@Builder
public class RegisterUserDto {
    private String email;
    
    private String password;
    
    private String fullName;
    
    // getters and setters here...
}