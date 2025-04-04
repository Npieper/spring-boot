package com.example.spring_boot.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {
    private String email;
    
    private String password;
    
    // getters and setters here...
}