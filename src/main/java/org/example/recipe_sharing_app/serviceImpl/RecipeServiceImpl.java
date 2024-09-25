package org.example.recipe_sharing_app.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CommentDto;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.model.*;
import org.example.recipe_sharing_app.repository.*;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final InfoGetter infoGetter;
    private final RecipeRepository recipeRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;


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

    @Transactional
    @Override
    public ResponseEntity<Recipe> getRecipe(String id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Not found"));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Override
    public List<Recipe> searchRecipe(String keyword) {
        return recipeRepository.searchRecipe(keyword);
    }

    @Transactional
    @Override
    public ResponseEntity<?> rateRecipe(String id, GetUserDto.RatingDto ratingDto) {
        Recipe recipe = getRecipeById(id);
        User loggedInUser = infoGetter.getLoggedInUser();
        Rating rating = Rating.builder()
                .id(UUID.randomUUID().toString())
                .rating(ratingDto.getRating())
                .recipe(recipe)
                .user(loggedInUser)
                .build();
        ratingRepository.save(rating);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> commentRecipe(String id, CommentDto commentDto) {
        Recipe recipeById = getRecipeById(id);
        User loggedInUser = infoGetter.getLoggedInUser();
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .recipe(recipeById)
                .user(loggedInUser)
                .message(commentDto.getComment())
                .parent(null)
                .build();
        commentRepository.save(comment);
        return new ResponseEntity<>(recipeById, HttpStatus.OK);
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

    private Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Not found"));
    }

}

