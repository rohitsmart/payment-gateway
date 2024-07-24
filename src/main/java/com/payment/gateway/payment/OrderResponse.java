package com.payment.gateway.payment;

import com.payment.gateway.entity.ModeOfPayment;
import com.payment.gateway.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderResponse {
    private String message;
    private String OrderId;
    private BigDecimal amount;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private ModeOfPayment modeOfPayment;
}
