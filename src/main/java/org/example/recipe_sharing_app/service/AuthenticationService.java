package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?>registerUser(RegisterDto registerDto);
}
