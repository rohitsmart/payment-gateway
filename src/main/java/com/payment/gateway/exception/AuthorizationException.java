package com.payment.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationException extends Throwable {
    private String message;
}
