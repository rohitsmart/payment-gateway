package com.payment.gateway.payment;

import com.payment.gateway.entity.Payment;
import com.payment.gateway.entity.PaymentStatus;
import com.payment.gateway.entity.User;
import com.payment.gateway.exception.ClientNotFoundException;
import com.payment.gateway.repository.ClientRepository;
import com.payment.gateway.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    public OrderResponse fetchOrderDetails(OrderRequest orderRequest) {
        try{
            var client = clientRepository.findBySecretKey(orderRequest.getSecretKey()).orElseThrow(
                    () -> new ClientNotFoundException("Client with secret key not found")
            );
            var paymentInitiate= Payment.builder()
                    .paymentStatus(PaymentStatus.PENDING)
                    .modeOfPayment(orderRequest.getModeOfPayment())
                    .amount(orderRequest.getAmount())
                    .client(client)
                    .orderId(orderRequest.getOrderId())
                    .build();
            var post= paymentRepository.save(paymentInitiate);
            return OrderResponse.builder()
                    .message("Complete Your Payment:")
                    .amount(post.getAmount())
                    .OrderId(post.getOrderId())
                    .transactionId(post.getTransactionId())
                    .paymentStatus(post.getPaymentStatus())
                    .modeOfPayment(post.getModeOfPayment())
                    .build();
        }catch (Exception e)
        {
            throw new RuntimeException("error :"+e.getLocalizedMessage());
        }
    }

    public OrderResponse fetchOrderDetailsByTID(String transactionID) {
        try{
            var post = paymentRepository.findByTransactionId(transactionID).orElseThrow(
                    ()-> new RuntimeException("Transaction ID is not found:")
            );
            return OrderResponse.builder()
                    .message("Complete Your Payment:")
                    .amount(post.getAmount())
                    .OrderId(post.getOrderId())
                    .transactionId(post.getTransactionId())
                    .paymentStatus(post.getPaymentStatus())
                    .modeOfPayment(post.getModeOfPayment())
                    .build();
        }catch (Exception e)
        {
            throw new RuntimeException("error :"+e.getLocalizedMessage());
        }
    }
}
