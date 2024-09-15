package org.example.recipe_sharing_app.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    
    ResponseEntity<?> userWithDetails(String id);
}
