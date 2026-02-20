package com.BookMySlot.util;

import com.BookMySlot.dto.UserRequestDTO;
import com.BookMySlot.dto.UserResponseDTO;
import com.BookMySlot.dto.UserUpdateDTO;
import com.BookMySlot.entity.User;

public class UserMapper {

    // it Convert UserRequestDTO to User Entity
    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPasswordHash(dto.getPassword());  // Will be hashed in service
        user.setRole(dto.getRole());
        user.setCity(dto.getCity());
        user.setLatitude(dto.getLatitude());
        user.setLongitude(dto.getLongitude());
        return user;
    }

    //it  Convert User Entity to UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        // NOTE: passwordHash is NOT included!
        dto.setRole(user.getRole());
        dto.setCity(user.getCity());
        dto.setLatitude(user.getLatitude());
        dto.setLongitude(user.getLongitude());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }

    // it Update User Entity from UserUpdateDTO
    public static void updateEntityFromDTO(User user, UserUpdateDTO dto) {
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getCity() != null) {
            user.setCity(dto.getCity());
        }
        if (dto.getLatitude() != null) {
            user.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            user.setLongitude(dto.getLongitude());
        }
    }
}