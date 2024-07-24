package com.payment.gateway.auth;

import com.payment.gateway.entity.User;
import com.payment.gateway.entity.UserRole;
import com.payment.gateway.repository.UserRepository;
import com.payment.gateway.security.AppProperties;
import com.payment.gateway.security.JwtService;
import com.payment.gateway.util.LoggerUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerUtil.getLogger(AuthService.class);
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        try {
            String username = request.getEmail();
            String password = request.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String bearerToken = jwtService.generateToken(user);
            logger.info("User {} logged in successfully", username);
            return LoginResponse.builder()
                    .token(bearerToken)
                    .build();
        } catch (BadCredentialsException e) {
            logger.error("Invalid username or password", e);
            throw new BadCredentialsException("Invalid username or password.");
        } catch (Exception e) {
            logger.error("Error caught inside login: {}", e.getLocalizedMessage(), e);
            throw new RuntimeException("Error caught inside login: " + e.getLocalizedMessage());
        }
    }

    @PostConstruct
    public void ensureAdminExists() {
        Optional<User> existingAdmin = userRepository.findByRole(UserRole.ADMIN);
        if (existingAdmin.isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(UserRole.ADMIN)
                    .active(true)
                    .accountVerified(true)
                    .build();
            userRepository.save(admin);
            logger.info("Admin user created");
        }
    }

    public LoginResponse register(LoginRequest request) {
        try {
            User newUser = User.builder()
                    .username(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(UserRole.USER)
                    .active(true)
                    .accountVerified(false)
                    .build();
            userRepository.save(newUser);
            logger.info("User {} registered successfully", request.getEmail());
            String bearerToken = jwtService.generateToken(newUser);
            logger.info("User {} registered in successfully", newUser.getUsername());
            return LoginResponse.builder()
                    .token(bearerToken)
                    .build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid username or password", e);
            throw new IllegalArgumentException("Invalid username or password." + e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("Error caught inside register: {}", e.getLocalizedMessage(), e);
            throw new RuntimeException("Error caught inside register: " + e.getLocalizedMessage());
        }
    }
}