package org.example.recipe_sharing_app.config;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final AuthEntryPoint authEntryPoint;
    private final JWTAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;

    public SecurityConfig(AuthEntryPoint authEntryPoint, JWTAuthFilter jwtAuthFilter,UserRepository userRepository){
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        DefaultSecurityFilterChain defaultSecurityFilterChain = http
                .csrf(e-> e.disable())
                .exceptionHandling(e->e
                        .authenticationEntryPoint(authEntryPoint))
                .sessionManagement(e->e
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(e-> e
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        return defaultSecurityFilterChain;
    }


    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(UserRepository userRepository){
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(users(userRepository));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
