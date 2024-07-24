package com.payment.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderId;

    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private ModeOfPayment modeOfPayment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private BigDecimal amount;

    private String currency;

    private String paymentReference;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String generateTransactionId() {
        Random random = new Random();
        StringBuilder transactionId = new StringBuilder(16);
        char[] transactionIdArray = new char[16];

        // Append 6 random digits
        for (int i = 0; i < 6; i++) {
            transactionIdArray[i] = (char) ('0' + random.nextInt(10));
        }

        // Append 5 random uppercase letters
        for (int i = 6; i < 11; i++) {
            transactionIdArray[i] = (char) ('A' + random.nextInt(26));
        }

        // Append 5 random lowercase letters
        for (int i = 11; i < 16; i++) {
            transactionIdArray[i] = (char) ('a' + random.nextInt(26));
        }

        // Shuffle the array
        for (int i = 0; i < transactionIdArray.length; i++) {
            int randomIndex = random.nextInt(transactionIdArray.length);
            char temp = transactionIdArray[i];
            transactionIdArray[i] = transactionIdArray[randomIndex];
            transactionIdArray[randomIndex] = temp;
        }

        return new String(transactionIdArray);
    }

}
