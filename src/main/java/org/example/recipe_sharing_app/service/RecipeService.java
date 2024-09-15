package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Recipe;
import org.springframework.http.ResponseEntity;

public interface RecipeService {

    ResponseEntity<?> createRecipe(CreateRecipeRequestDto recipeRequestDto);
}
