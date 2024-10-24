package org.example.recipe_sharing_app.util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.requestDto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.exception.EntityNotFoundException;
import org.example.recipe_sharing_app.model.Ingredient;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.IngredientRepository;
import org.example.recipe_sharing_app.repository.RecipeRepository;
import org.example.recipe_sharing_app.repository.SavedRecipeRepository;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InfoGetter {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final SavedRecipeRepository savedRecipeRepository;

    @Transactional
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String name = authentication.getName();
            return userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return null;
    }


    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Not found"));
    }

    @Transactional
    public Ingredient getOrCreate(CreateRecipeRequestDto.CreateIngredientDto ingredientDto
            , List<Ingredient> ingredients
    ) {
        return ingredientRepository.findByName(ingredientDto.getIngredientName())
                .orElseGet(()->{
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(UUID.randomUUID().toString());
                    ingredient.setName(ingredientDto.getIngredientName());
                    ingredients.add(ingredient);
                    return ingredient;
                });
    }

    @Transactional
    public List<?> getSavedRecipeByUserId(String id) {
        return Collections.singletonList((savedRecipeRepository.findByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("Not Found"))));
    }
}
