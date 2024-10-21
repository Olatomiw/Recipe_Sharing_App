package org.example.recipe_sharing_app.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.dto.UpdatePasswordRequest;
import org.example.recipe_sharing_app.dto.UpdateUserRequest;
import org.example.recipe_sharing_app.exception.EntityNotFoundException;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.UserService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final InfoGetter infoGetter;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public ResponseEntity<?> userWithDetails(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found"));

        List<GetUserDto.RecipeDto> recipeDto = user.getMy_recipes()
                .stream()
                .map(this::mapToRecipeDto)
                .collect(Collectors.toList());

        GetUserDto userDto = GetUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFirstname() + " " + user.getLastname())
                .recipeDtoList(recipeDto)
                .build();
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

    @Override
    public ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest, Authentication authentication) {
        String name = authentication.getName();
        User user= infoGetter.getLoggedInUser();
        if (!name.equals(user.getEmail())){
            return new ResponseEntity<>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
        }
        user.setFirstname(updateUserRequest.getFirstname());
        user.setLastname(updateUserRequest.getLastname());
        user.setEmail(updateUserRequest.getEmail());
        userRepository.save(user);


        return null;
    }

    private GetUserDto.RecipeDto mapToRecipeDto(Recipe recipe){
        List<GetUserDto.RecipeIngredientDto> ingredientDtos = recipe.getMyRecipeIngredients().stream()
                .map(recipeIngredient -> new GetUserDto.RecipeIngredientDto(
                        recipeIngredient.getIngredient().getName(),
                        recipeIngredient.getQuantity()
                )).toList();
        List<GetUserDto.CommentDto> commentDtos = recipe.getMyComments().stream()
                .map(comment -> new GetUserDto.CommentDto(
                        comment.getId(),
                        comment.getMessage(),
                        comment.getCreated_at().toString(),
                        comment.getLikes(),
                        comment.getDislikes()
                )).toList();
        List<GetUserDto.RatingDto> ratingDtos = recipe.getMyRatings().stream()
                .map(rating -> new GetUserDto.RatingDto(
                        rating.getId(),
                        rating.getRating()
                )).toList();

        return new GetUserDto.RecipeDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(),
                ingredientDtos,
                commentDtos,
                ratingDtos
        );
    }
}
