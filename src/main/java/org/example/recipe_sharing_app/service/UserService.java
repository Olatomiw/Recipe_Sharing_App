package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.requestDto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.dto.requestDto.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    
    ResponseEntity<?> userWithDetails(String id);
    ResponseEntity<?>changePassword(
            UpdatePasswordRequest updatePasswordRequest, Authentication authentication);
    ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest, Authentication authentication);
}
