package com.payment.gateway.repository;

import com.payment.gateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    @Query("select p from Payment p where p.transactionId = ?1")
    Optional<Payment> findByTransactionId(String transactionId);
}
