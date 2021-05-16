package com.bargain.users.controller;

import com.bargain.users.client.AuthController;
import com.bargain.users.client.dto.request.LoginRequestDto;
import com.bargain.users.client.dto.request.RegisterRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import com.bargain.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Slf4j
public class AuthControllerImpl implements AuthController {

    @Autowired
    private UserService userService;

    @Override
    public void login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("About to authenticate user with email {}", loginRequestDto.getEmail());
        userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @Override
    public RegisterResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("About to create a user with email {}", registerRequestDto.getEmail());
        String userRef = userService.register(registerRequestDto.getEmail(), registerRequestDto.getPassword());
        return RegisterResponseDto.builder().ref(userRef).build();
    }
}
