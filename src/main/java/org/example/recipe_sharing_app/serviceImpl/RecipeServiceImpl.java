package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Ingredient;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.RecipeIngredient;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.IngredientRepository;
import org.example.recipe_sharing_app.repository.RecipeIngredientRepository;
import org.example.recipe_sharing_app.repository.UserRepository;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeIngredientRepository recipeIngredientRepository;

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
        List<RecipeIngredient> myRecipeIngredients = new ArrayList<>();

        for(CreateRecipeRequestDto.CreateIngredientDto ingredientDto
                : recipeRequestDto.getIngredients()) {
            Ingredient ingredient;
            if (ingredientDto.getId()!=null){
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientDto.getId());
                if (optionalIngredient.isPresent()) {
                    ingredient = optionalIngredient.get();

                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setId(UUID.randomUUID().toString());
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setRecipe(newRecipe);
                    myRecipeIngredients.add(recipeIngredient);
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
                }
            } else if ( ingredientDto.getIngredientName()!=null) {
                   Ingredient ingredients = new Ingredient();
                   ingredients.setId(UUID.randomUUID().toString());
                   ingredients.setName(ingredientDto.getIngredientName());

                   RecipeIngredient recipeIngredient = new RecipeIngredient();
                   recipeIngredient.setId(UUID.randomUUID().toString());
                   recipeIngredient.setRecipe(newRecipe);
                   recipeIngredient.setIngredient(ingredients);
                   recipeIngredient.setQuantity(ingredientDto.getQuantity());
                   myRecipeIngredients.add(recipeIngredient);
                ingredientRepository.save(ingredients);
            }
        }
        newRecipe.setMyRecipeIngredients(myRecipeIngredients);
        myRecipes.add(newRecipe);
        User savedRecipe = userRepository.save(user);
        recipeIngredientRepository.saveAll(myRecipeIngredients);
        return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
    }
}
