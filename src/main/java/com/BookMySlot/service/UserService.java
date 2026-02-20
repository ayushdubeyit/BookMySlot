package com.BookMySlot.service;

import com.BookMySlot.dto.UserRequestDTO;
import com.BookMySlot.dto.UserResponseDTO;
import com.BookMySlot.dto.UserUpdateDTO;
import com.BookMySlot.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO requestDTO);
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long userId, UserUpdateDTO updateDTO);
    void deleteUser(Long userId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}