package com.bargain.users.client;

import com.bargain.users.client.dto.request.LoginRequestDto;
import com.bargain.users.client.dto.request.RegisterRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class AuthClient implements AuthController {

    private RestTemplate restTemplate;
    private String url;

    public AuthClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public void login(@Valid LoginRequestDto loginRequestDto) {
        Map<String, String> pathParams = new HashMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromHttpUrl(this.url + AuthController.PATH + "/login")
                .queryParams(queryParams)
                .build(pathParams)
                .toString();

        HttpEntity<LoginRequestDto> requestEntity = new HttpEntity<>(loginRequestDto);

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
    }

    @Override
    public RegisterResponseDto register(@Valid RegisterRequestDto registerRequestDto) {
        Map<String, String> pathParams = new HashMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromHttpUrl(this.url + AuthController.PATH + "/register")
                .queryParams(queryParams)
                .build(pathParams)
                .toString();

        HttpEntity<RegisterRequestDto> requestEntity = new HttpEntity<>(registerRequestDto);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                RegisterResponseDto.class
        ).getBody();
    }
}
