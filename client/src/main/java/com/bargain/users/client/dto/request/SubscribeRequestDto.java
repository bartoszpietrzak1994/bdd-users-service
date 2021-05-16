package com.bargain.users.client.dto.request;

import com.bargain.users.client.dto.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubscribeRequestDto {

    @NotBlank
    private String userRef;

    @NotNull
    private SubscriptionStatus subscriptionStatus;
}
