package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.requestDto.CommentDto;
import org.example.recipe_sharing_app.dto.requestDto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.requestDto.GetUserDto;
import org.example.recipe_sharing_app.dto.responseDto.RecipeResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {

    ResponseEntity<?> createRecipe(CreateRecipeRequestDto recipeRequestDto);

    ResponseEntity<?> getRecipe(String id);

    List<RecipeResponseDto> searchRecipe(String keyword);

    ResponseEntity<?> rateRecipe(String id, GetUserDto.RatingDto ratingDto);

    ResponseEntity<?> commentRecipe(String id, CommentDto commentDto);

    ResponseEntity<?> saveRecipe(String recipeId);

    ResponseEntity<?> getSavedRecipe();
}
