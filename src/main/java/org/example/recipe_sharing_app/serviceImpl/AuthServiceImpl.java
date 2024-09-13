package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.RegisterDto;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstname(registerDto.getFirstName());
        user.setLastname(registerDto.getLastName());
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
