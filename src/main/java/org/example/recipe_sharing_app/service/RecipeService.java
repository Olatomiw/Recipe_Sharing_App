package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Recipe;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {

    ResponseEntity<?> createRecipe(CreateRecipeRequestDto recipeRequestDto);

    ResponseEntity<Recipe> getRecipe(String id);

    List<Recipe> searchRecipe(String keyword);
}
