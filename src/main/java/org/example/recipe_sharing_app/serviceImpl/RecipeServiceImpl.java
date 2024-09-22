package org.example.recipe_sharing_app.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Ingredient;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.RecipeIngredient;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.IngredientRepository;
import org.example.recipe_sharing_app.repository.RecipeIngredientRepository;
import org.example.recipe_sharing_app.repository.RecipeRepository;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeIngredientRepository recipeIngredientRepository;

    private final InfoGetter infoGetter;
    private final RecipeRepository recipeRepository;


    @Transactional
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
        List<Ingredient> newIngredients = new ArrayList<>();

        List<RecipeIngredient> recipeIngredients = recipeRequestDto.getIngredients()
                .stream()
                .map(ingredientDto->{
                    Ingredient ingredient =getOrCreate(ingredientDto, newIngredients);
                    return RecipeIngredient.builder()
                            .id(UUID.randomUUID().toString())
                            .ingredient(ingredient)
                            .recipe(newRecipe)
                            .quantity(ingredientDto.getQuantity())
                            .build();
                }).collect(Collectors.toList());

        if (!newIngredients.isEmpty()){
            ingredientRepository.saveAll(newIngredients);
        }

        newRecipe.setMyRecipeIngredients(recipeIngredients);
        myRecipes.add(newRecipe);

        User save = userRepository.save(user);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }


    private Ingredient getOrCreate(CreateRecipeRequestDto.CreateIngredientDto ingredientDto
            , List<Ingredient>ingredients
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
}

