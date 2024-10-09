package org.example.recipe_sharing_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody
            UpdatePasswordRequest updatePasswordRequest, Authentication authentication) {
       return userService.changePassword(updatePasswordRequest, authentication);
    }

    @GetMapping("/getUserDetails/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable String id) {
        return userService.userWithDetails(id);
    }
}
