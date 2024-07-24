package com.payment.gateway.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private String secretKey;
    private String expireDate;
}
