package com.payment.gateway.repository;

import com.payment.gateway.entity.User;
import com.payment.gateway.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(UserRole role);
}
