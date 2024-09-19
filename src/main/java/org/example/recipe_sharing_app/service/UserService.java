package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.dto.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    
    ResponseEntity<?> userWithDetails(String id);
    ResponseEntity<?>changePassword(
            UpdatePasswordRequest updatePasswordRequest, Authentication authentication);
    ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest, Authentication authentication);
}
