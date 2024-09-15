package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.config.JWTConfig;
import org.example.recipe_sharing_app.dto.AuthResponseDto;
import org.example.recipe_sharing_app.dto.LoginDto;
import org.example.recipe_sharing_app.dto.RegisterDto;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.AuthenticationService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;
    private final InfoGetter infoGetter;


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

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),loginDto.getPassword()));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        String generatedToken = jwtConfig.generateToken(authentication);
        String username =  jwtConfig.extractUsername(generatedToken);
        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .token(generatedToken)
                .username(username)
                .build();
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(String oldPassword, String newPassword) {
        User user= infoGetter.getLoggedInUser().getBody();
        if (user != null ) {
            passwordEncoder.matches(oldPassword,user.getPassword());
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }

        return null;
    }


}
