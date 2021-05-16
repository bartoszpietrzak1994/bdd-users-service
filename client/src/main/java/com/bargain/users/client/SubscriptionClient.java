package com.bargain.users.client;

import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.request.SubscribeRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionClient implements SubscriptionController {

    private RestTemplate restTemplate;
    private String url;

    public SubscriptionClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public SubscriptionStatus getSubscription(String userRef) {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("userRef", userRef);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromHttpUrl(this.url + SubscriptionController.PATH + "/user/{userRef}")
                .queryParams(queryParams)
                .build(pathParams)
                .toString();

        return restTemplate.getForEntity(url, SubscriptionStatus.class).getBody();
    }

    @Override
    public SubscriptionStatus subscribe(@Valid SubscribeRequestDto subscribeRequestDto) {
        Map<String, String> pathParams = new HashMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromHttpUrl(this.url + SubscriptionController.PATH + "/subscribe")
                .queryParams(queryParams)
                .build(pathParams)
                .toString();

        HttpEntity<SubscribeRequestDto> requestEntity = new HttpEntity<>(subscribeRequestDto);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                SubscriptionStatus.class
        ).getBody();
    }

    @Override
    public void cancel(String userRef) {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("userRef", userRef);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromHttpUrl(this.url + SubscriptionController.PATH + "user/{userRef}/cancel")
                .queryParams(queryParams)
                .build(pathParams)
                .toString();

        restTemplate.postForEntity(
                url,
                HttpMethod.POST,
                Void.class
        );
    }
}
