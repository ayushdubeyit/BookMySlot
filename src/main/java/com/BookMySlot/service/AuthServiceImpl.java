package com.BookMySlot.service;

import com.BookMySlot.dto.LoginRequestDTO;
import com.BookMySlot.dto.LoginResponseDTO;
import com.BookMySlot.entity.User;
import com.BookMySlot.exception.InvalidCredentialsException;
import com.BookMySlot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException());

        // Verify password
        boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getPassword(),  // Plain password from request
                user.getPasswordHash()        // Hashed password from database
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        // Update last login time
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Create response DTO
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());
        response.setMessage("Login successful");

        return response;
    }
}