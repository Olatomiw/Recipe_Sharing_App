package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    
    ResponseEntity<?> userWithDetails(String id);
    ResponseEntity<?>changePassword(UpdatePasswordRequest updatePasswordRequest, Authentication authentication);
}
