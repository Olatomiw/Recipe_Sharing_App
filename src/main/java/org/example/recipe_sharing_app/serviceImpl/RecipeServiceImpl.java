package org.example.recipe_sharing_app.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.recipe_sharing_app.dto.CommentDto;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.exception.DuplicateRecipeException;
import org.example.recipe_sharing_app.exception.EntityNotFoundException;
import org.example.recipe_sharing_app.model.*;
import org.example.recipe_sharing_app.repository.*;
import org.example.recipe_sharing_app.service.NotificationService;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final InfoGetter infoGetter;
    private final RecipeRepository recipeRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final SavedRecipeRepository savedRecipeRepository;


    @Transactional
    @Override
    public ResponseEntity<?> createRecipe(CreateRecipeRequestDto recipeRequestDto) {
       User user= infoGetter.getLoggedInUser();
        assert user != null;

        if (recipeRepository.findByTitleAndUser(recipeRequestDto.getRecipeName(), user).isPresent()){
            throw new DuplicateRecipeException("Already Exists", HttpStatus.CONFLICT.value());
        }

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
                    Ingredient ingredient =infoGetter.getOrCreate(ingredientDto, newIngredients);
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

        List<Recipe> myRecipes = new ArrayList<>();
        if (user.getMy_recipes() != null){
            user.getMy_recipes().add(newRecipe);
        }else {
            myRecipes.add(newRecipe);
            user.setMy_recipes(myRecipes);
        }
        Recipe save1 = recipeRepository.save(newRecipe);
        User save = userRepository.save(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ResponseEntity<Recipe> getRecipe(String id) {
        Recipe recipeById = infoGetter.getRecipeById(id);
        return new ResponseEntity<>(recipeById, HttpStatus.OK);
    }

    @Override
    public List<Recipe> searchRecipe(String keyword) {
        return recipeRepository.searchRecipe(keyword);
    }

    @Transactional
    @Override
    public ResponseEntity<?> rateRecipe(String id, GetUserDto.RatingDto ratingDto) {
        User loggedInUser = infoGetter.getLoggedInUser();
        Recipe recipe = infoGetter.getRecipeById(id);
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
        Recipe recipeById = infoGetter.getRecipeById(id);
        User loggedInUser = infoGetter.getLoggedInUser();
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .recipe(recipeById)
                .user(loggedInUser)
                .message(commentDto.getComment())
                .parent(null)
                .build();
        String message = loggedInUser.getFirstname() + " commented on your recipe";
        notificationService.createNotification(message, recipeById.getUser(), recipeById);
        commentRepository.save(comment);
        return new ResponseEntity<>(recipeById, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveRecipe(String id){
        User loggedInUser = infoGetter.getLoggedInUser();
        Recipe recipeById = infoGetter.getRecipeById(id);
        SavedRecipe savedRecipe = SavedRecipe.builder()
                .id(UUID.randomUUID().toString())
                .user(loggedInUser)
                .recipe(recipeById)
                .build();
        savedRecipeRepository.save(savedRecipe);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }
}

