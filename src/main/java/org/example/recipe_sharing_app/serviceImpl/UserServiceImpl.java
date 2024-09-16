package org.example.recipe_sharing_app.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.UserService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final InfoGetter infoGetter;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> userWithDetails(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        List<GetUserDto.RecipeDto> recipeDto = null;
        GetUserDto userDto = GetUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFirstname() + " " + user.getLastname())
                .recipeDtoList(recipeDto)
                .build();

        recipeDto = user.getMy_recipes()
                .stream()
                .map((recipe) -> GetUserDto.RecipeDto.builder()
                        .id(recipe.getId())
                        .title(recipe.getTitle())
                        .description(recipe.getDescription())
                        .build()).toList();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(UpdatePasswordRequest updatePasswordRequest, Authentication authentication)
    {
        String name = authentication.getName();
        User user= infoGetter.getLoggedInUser();

        if (!name.equals(user.getEmail())){
            return new ResponseEntity<>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())){
            return new ResponseEntity<>("Old password does not match", HttpStatus.BAD_REQUEST);
        }
        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())){
            return new ResponseEntity<>("Confirm password does not match", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
