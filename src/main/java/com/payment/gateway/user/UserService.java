package com.payment.gateway.user;

import com.payment.gateway.entity.Client;
import com.payment.gateway.entity.User;
import com.payment.gateway.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClientRepository clientRepository;
    public UserResponse createClient(UserRequest userRequest, User user) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(userRequest.getExpireDate(), formatter);
            var secretKey= Client.generateRandomString();
            var client = Client.builder()
                    .user(user)
                    .expireDate(date)
                    .secretKey(secretKey)
                    .build();
            var post= clientRepository.save(client);

            return  UserResponse.builder()
                    .expireDate(String.valueOf(post.getExpireDate()))
                    .secretKey(post.getSecretKey())
                    .build();
        }
        catch (Exception e)
        {
            throw new RuntimeException("error:"+e.getLocalizedMessage());
        }
    }

    public UserResponse fetchClient(User user) {
        try {
            Optional<Client> clientOptional = clientRepository.findByUser(user);

            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                return UserResponse.builder()
                        .expireDate(client.getExpireDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .secretKey(client.getSecretKey())
                        .build();
            } else {
                throw new RuntimeException("Client not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getLocalizedMessage());
        }
    }
}
