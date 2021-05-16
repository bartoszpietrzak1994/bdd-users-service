package com.bargain.users.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
