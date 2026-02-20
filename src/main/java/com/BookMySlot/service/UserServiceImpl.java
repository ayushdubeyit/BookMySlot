package com.BookMySlot.service;

import com.BookMySlot.dto.UserRequestDTO;
import com.BookMySlot.dto.UserResponseDTO;
import com.BookMySlot.dto.UserUpdateDTO;
import com.BookMySlot.entity.User;
import com.BookMySlot.exception.DuplicateResourceException;
import com.BookMySlot.exception.UserNotFoundException;
import com.BookMySlot.repository.UserRepository;
import com.BookMySlot.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        // Check duplicates
        if (existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email", requestDTO.getEmail());
        }

        if (existsByUsername(requestDTO.getUsername())) {
            throw new DuplicateResourceException("Username", requestDTO.getUsername());
        }

        // Convert DTO to Entity
        User user = UserMapper.toEntity(requestDTO);

        // Hash password BEFORE saving
        String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.setPasswordHash(hashedPassword);

        // Save to database
        User savedUser = userRepository.save(user);

        // Convert Entity to Response DTO
        return UserMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return UserMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserMapper.updateEntityFromDTO(user, updateDTO);

        User updatedUser = userRepository.save(user);

        return UserMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}