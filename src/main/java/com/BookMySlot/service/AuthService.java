package com.BookMySlot.service;

import com.BookMySlot.dto.LoginRequestDTO;
import com.BookMySlot.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}