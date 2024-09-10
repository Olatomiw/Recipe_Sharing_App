package org.example.recipe_sharing_app.config;

import jakarta.persistence.Column;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        DefaultSecurityFilterChain securityFilterChain = http
                .csrf(e->e.disable())
                .authorizeHttpRequests(e->e
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .build();

        return securityFilterChain;
    }
}
