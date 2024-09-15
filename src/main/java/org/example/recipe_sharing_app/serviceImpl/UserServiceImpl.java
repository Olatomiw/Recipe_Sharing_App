package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
}
