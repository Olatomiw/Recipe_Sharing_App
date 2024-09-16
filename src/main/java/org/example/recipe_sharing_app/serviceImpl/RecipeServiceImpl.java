package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.RecipeRepository;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;

    private final InfoGetter infoGetter;


    @Override
    public ResponseEntity<?> createRecipe(CreateRecipeRequestDto recipeRequestDto) {
       User user= infoGetter.getLoggedInUser();
        assert user != null;
        List<Recipe> myRecipes = user.getMy_recipes();
        Recipe newRecipe = Recipe.builder()
               .id(UUID.randomUUID().toString())
                .user(user)
               .image(recipeRequestDto.getImage())
               .title(recipeRequestDto.getRecipeName())
               .description(recipeRequestDto.getDescription())
               .instructions(recipeRequestDto.getInstructions())
               .build();
        myRecipes.add(newRecipe);
       User savedRecipe = userRepository.save(user);
        return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
    }
}
