package com.payment.gateway.repository;

import com.payment.gateway.entity.Client;
import com.payment.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    @Query("select c from Client c where c.user = ?1")
    Optional<Client> findByUser(User user);

    @Query("select c from Client c where c.secretKey = ?1")
    Optional<Client> findBySecretKey(String secretKey);
}
