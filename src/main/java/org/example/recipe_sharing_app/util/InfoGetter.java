package org.example.recipe_sharing_app.util;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InfoGetter {

    private final UserRepository userRepository;

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String name = authentication.getName();
            return userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return null;
    }
}
