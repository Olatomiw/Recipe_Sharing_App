package org.example.recipe_sharing_app.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.recipe_sharing_app.dto.requestDto.CommentDto;
import org.example.recipe_sharing_app.dto.requestDto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.requestDto.GetUserDto;
import org.example.recipe_sharing_app.dto.responseDto.RecipeResponseDto;
import org.example.recipe_sharing_app.exception.DuplicateRecipeException;
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

        Set<RecipeIngredient> recipeIngredients = recipeRequestDto.getIngredients()
                .stream()
                .map(ingredientDto->{
                    Ingredient ingredient =infoGetter.getOrCreate(ingredientDto, newIngredients);
                    return RecipeIngredient.builder()
                            .id(UUID.randomUUID().toString())
                            .ingredient(ingredient)
                            .recipe(newRecipe)
                            .quantity(ingredientDto.getQuantity())
                            .build();
                }).collect(Collectors.toSet());
        if (!newIngredients.isEmpty()){
            ingredientRepository.saveAll(newIngredients);
        }
        newRecipe.setMyRecipeIngredients(recipeIngredients);

        Set<Recipe> myRecipes = new HashSet<>();
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

    @Override
    @Transactional
    public ResponseEntity<?> getRecipe(String id) {
        Recipe recipeById = infoGetter.getRecipeById(id);
        RecipeResponseDto requestDto = new RecipeResponseDto(
                recipeById.getTitle(),
                recipeById.getDescription(),
                recipeById.getInstructions(),
                recipeById.getImage(),
                recipeById.getMyRecipeIngredients()
        );
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @Transactional
    @Override
    public List<RecipeResponseDto> searchRecipe(String keyword) {
        List<Recipe> recipes = recipeRepository.searchRecipe(keyword);
        return recipes.stream()
                .map((recipe)-> new RecipeResponseDto(
                        recipe.getTitle(), recipe.getDescription(), recipe.getInstructions(), recipe.getImage(), recipe.getMyRecipeIngredients()
                )).collect(Collectors.toList());
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

    @Transactional
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

    @Transactional
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
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ResponseEntity<?> getSavedRecipe(){
        User loggedInUser = infoGetter.getLoggedInUser();
        List<?> savedRecipeByUserId = infoGetter.getSavedRecipeByUserId(loggedInUser.getId());
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}

