package com.example.spring_boot.service;

import com.example.spring_boot.DTO.RegisterUserDto;
import com.example.spring_boot.entity.User;
import com.example.spring_boot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService; // Testet nur die Logik dieser Klasse

    private static final String EMAIL = "abc@gmail.com";

    @Test
    public void testSignup() {
        User user = User.builder().id(1)
                .email("abc@gmail.com")
                .fullName("Test User")
                .password("123")
                .build();

        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .email(EMAIL)
                .password("12")
                .fullName("Hallo")
                .build();


       // when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0); // Der User, der tatsächlich gespeichert wird
        });


        // Act
        User savedUser = authenticationService.signup(registerUserDto);

        assertNotNull(savedUser);
        assertEquals(EMAIL, savedUser.getUsername());
        verify(userRepository, times(1)).save(savedUser);
        verify(passwordEncoder, times(1)).encode(registerUserDto.getPassword());

    }
/*
    @Test
    public void testLogin_WithValidUser() {
        // Arrange
        User user = new User("testuser", "password");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Act
        User foundUser = authService.login("testuser", "password");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testLogin_WithInvalidUser() {
        // Arrange
        when(userRepository.findByUsername("wronguser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.login("wronguser", "password"));
    } */
}
