package com.payment.gateway.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private double amount;
    private String currency;
    private String orderId;
    private String description;
    private String clientKey;
}
