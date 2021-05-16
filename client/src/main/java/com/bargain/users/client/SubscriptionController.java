package com.bargain.users.client;

import com.bargain.users.Constants;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.request.SubscribeRequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(SubscriptionController.PATH)
public interface SubscriptionController {

    String PATH = Constants.V1_API_PREFIX + "/subscription";

    @GetMapping(path = "/user/{userRef}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SubscriptionStatus getSubscription(@PathVariable String userRef);

    @PostMapping(
            value = "/subscribe",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    SubscriptionStatus subscribe(@Valid @RequestBody SubscribeRequestDto subscribeRequestDto);

    @PostMapping(
            value = "/user/{userRef}/cancel",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    void cancel(@PathVariable String userRef);
}
