package com.bargain.users.client;

import com.bargain.users.Constants;
import com.bargain.users.client.dto.request.LoginRequestDto;
import com.bargain.users.client.dto.request.RegisterRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(AuthController.PATH)
public interface AuthController {

    String PATH = Constants.V1_API_PREFIX + "/auth";

    @PostMapping(
            path = "/login",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    void login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @PostMapping(
            path = "/register",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto);
}
