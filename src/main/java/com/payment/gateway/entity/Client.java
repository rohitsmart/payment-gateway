package com.payment.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate expireDate;
    private String secretKey;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        String capitalLetters = random.ints(3, 'A', 'Z' + 1)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
        String lowercaseLetters = random.ints(5, 'a', 'z' + 1)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
        String combined = capitalLetters + lowercaseLetters;
        return random.ints(8, 0, combined.length())
                .mapToObj(combined::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
