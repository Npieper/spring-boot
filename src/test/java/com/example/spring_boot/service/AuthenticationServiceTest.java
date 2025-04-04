package com.example.spring_boot.service;

import com.example.spring_boot.DTO.LoginUserDto;
import com.example.spring_boot.DTO.RegisterUserDto;
import com.example.spring_boot.entity.User;
import com.example.spring_boot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private  AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService; // Testet nur die Logik dieser Klasse

    private static final String EMAIL = "abc@gmail.com";
    private static final String PASSWORD = "123";
    private static final String FULL_NAME = "Max Mustermann";

    @Test
    public void signup_ShouldSaveUserWithEncodedPassword() {
        User user = User.builder().id(1)
                .email("abc@gmail.com")
                .fullName("Test User")
                .password("123")
                .build();

        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .fullName(FULL_NAME)
                .build();

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

    @Test
    public void authenticate_ShouldFindUserByEmail() {
        LoginUserDto loginUser = LoginUserDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        User user = User.builder().id(1)
                .email(EMAIL)
                .fullName(FULL_NAME)
                .password(PASSWORD)
                .build();


        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        // Act
        User savedUser = authenticationService.authenticate(loginUser);

        // Verify that UsernamePasswordAuthenticationToken gets called with right parameter
        verify(authenticationManager).authenticate(captor.capture());
        UsernamePasswordAuthenticationToken capturedToken = captor.getValue();
        assertEquals(EMAIL, capturedToken.getPrincipal());
        assertEquals(PASSWORD, capturedToken.getCredentials());

        assertTrue(passwordEncoder.matches(PASSWORD, savedUser.getPassword()));


        verify(userRepository, times(1)).findByEmail(loginUser.getEmail());
        assertEquals(FULL_NAME, savedUser.getFullName());
        assertEquals(EMAIL, savedUser.getUsername());
    }
}
