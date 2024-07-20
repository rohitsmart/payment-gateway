package com.payment.gateway.security;

import com.payment.gateway.entity.User;
import com.payment.gateway.repository.UserRepository;
import com.payment.gateway.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetail {

    private static final Logger logger = LoggerUtil.getLogger(UserDetail.class);

    private final UserRepository userRepository;

    public User getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
            logger.info("Retrieved user '{}' successfully", username);
            return user;
        } catch (Exception e) {
            logger.error("Error retrieving user details: {}", e.getMessage());
            throw new RuntimeException("User Not Found Or Token is Missing");
        }
    }
}