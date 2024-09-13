package org.example.recipe_sharing_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.LoginDto;
import org.example.recipe_sharing_app.dto.RegisterDto;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody RegisterDto registerDto) {
       return authenticationService.registerUser(registerDto);
    };

    @PostMapping("/login")
    public ResponseEntity<?> loginUser( @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }
}
