package com.ilabs.AuthService.service;

import com.ilabs.AuthService.dto.UserDto;
import com.ilabs.AuthService.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> signUp(UserDto userDto);

    ResponseEntity<?> login(UserLoginDto userLoginDto);
}