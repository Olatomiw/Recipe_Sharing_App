package org.example.recipe_sharing_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
