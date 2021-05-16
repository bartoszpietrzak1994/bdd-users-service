package com.bargain.users.controller;

import com.bargain.users.client.SubscriptionController;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.request.SubscribeRequestDto;
import com.bargain.users.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Slf4j
public class SubscriptionControllerImpl implements SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public SubscriptionStatus getSubscription(@PathVariable String userRef) {
        log.info("About to get subscription status for user with email {}", userRef);
        return subscriptionService.getSubscriptionForUser(userRef);
    }

    @Override
    public SubscriptionStatus subscribe(@RequestBody SubscribeRequestDto subscribeRequestDto) {
        log.info("About to activate subscription {} for user with ref {}",
                subscribeRequestDto.getSubscriptionStatus().name(), subscribeRequestDto.getUserRef());
        return subscriptionService.subscribe(
                subscribeRequestDto.getUserRef(),
                subscribeRequestDto.getSubscriptionStatus()
        );
    }

    @Override
    public void cancel(@PathVariable String userRef) {
        log.info("About to cancel current subscription for user with ref {}", userRef);
        subscriptionService.cancel(userRef);
    }
}
