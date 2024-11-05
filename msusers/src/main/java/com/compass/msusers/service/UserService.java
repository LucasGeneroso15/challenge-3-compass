package com.compass.msusers.service;

import com.compass.msusers.exceptions.EqualPasswordInvalidException;
import com.compass.msusers.exceptions.InvalidZipCodeException;
import com.compass.msusers.exceptions.PasswordInvalidException;
import com.compass.msusers.exceptions.UsernameNotFoundException;
import com.compass.msusers.web.dto.ViaCepResponse;
import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.Address;
import com.compass.msusers.repository.UserRepository;
import com.compass.msusers.web.openFeign.ViaCepClientOpenFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ViaCepClientOpenFeign client;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Address getAddressByZipCode(String cep) {
        if (!cep.matches("^[0-9]{8}$")) {
            throw new InvalidZipCodeException("Zip code must be exactly 8 digits (0-9)");
        }

        ViaCepResponse response = client.getAddressByZipCode(cep);

        if (response == null || response.getErro()) {
            throw new InvalidZipCodeException("Invalid or not found ZIP code: " + cep);
        }

        return new Address(
                response.getCep(),
                response.getLogradouro(),
                response.getComplemento(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf()
        );
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

       String message = String.format("'%s', CREATE", user.getUsername());
       kafkaTemplate.send("msusers_topic", message);
       log.info("Sent: {}", message);
       return user;
    }

    public void editPassword(String username, String oldPassword, String newPassword) {
        if (newPassword.equals(oldPassword)) {
            throw new EqualPasswordInvalidException("The new password cannot be the same as the current password. Please check and try again.");
        }

        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordInvalidException("The old password does not match the recorded password. Please check and try again.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

       String message = String.format("'%s', UPDATE", user.getUsername());
       kafkaTemplate.send("msusers_topic", message);
       log.info("Sent: {}", message);
    }
}
