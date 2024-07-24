package com.payment.gateway.payment;

import com.payment.gateway.entity.ModeOfPayment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {
    private String OrderId;
    private BigDecimal amount;
    private String secretKey;
    private ModeOfPayment modeOfPayment;
    private String transactionId;

}
