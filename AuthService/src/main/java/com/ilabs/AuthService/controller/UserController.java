package com.ilabs.AuthService.controller;

import com.ilabs.AuthService.dto.UserDto;
import com.ilabs.AuthService.dto.UserLoginDto;
import com.ilabs.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@RequestBody(required = true) UserDto userDto) {
        return userService.signUp(userDto);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> login(@RequestBody(required = true) UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }



}