package org.example.recipe_sharing_app.serviceImpl;

import org.example.recipe_sharing_app.dto.CommentDto;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.GetUserDto;
import org.example.recipe_sharing_app.exception.DuplicateRecipeException;
import org.example.recipe_sharing_app.model.Comment;
import org.example.recipe_sharing_app.model.Rating;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.*;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.util.InfoGetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private InfoGetter infoGetter;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private CreateRecipeRequestDto createRecipeRequestDto;
    private User user;
    private Recipe newRecipe;
    private String recipeId;

    @BeforeEach
    void setUp() {
        createRecipeRequestDto = CreateRecipeRequestDto.builder()
                .recipeName("My Recipe")
                .description("My Description")
                .instructions("My Instructions")
                .image("My Image")
                .build();
        CreateRecipeRequestDto.CreateIngredientDto createIngredientDto = CreateRecipeRequestDto.CreateIngredientDto
                .builder()
                .ingredientName("My Ingredient")
                .quantity("200 l")
                .build();
        createRecipeRequestDto.setIngredients(List.of(createIngredientDto));
        user = User.builder()
                .id(UUID.randomUUID().toString())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("Olatomiwa2$$$")
                .build();
        recipeId = UUID.randomUUID().toString();
        newRecipe = Recipe.builder()
                .id(recipeId)
                .title("recipe")
                .description("description")
                .instructions("instructions")
                .image("image")
                .user(user)
                .build();
    }

    @Test
  void CreateRecipe_ShouldCreateSuccessfully() {
        when(infoGetter.getLoggedInUser()).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(newRecipe);

        ResponseEntity<?> recipe = recipeService.createRecipe(createRecipeRequestDto);
       User user1 = (User) recipe.getBody();
        HttpStatusCode status = recipe.getStatusCode();

        assertNotNull(recipe);
        assertEquals(HttpStatus.CREATED, status);
        assertNotNull(user1);
        assertNotNull(recipe.getStatusCode());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void ShouldThrow_DuplicateRecipeException(){

        when(infoGetter.getLoggedInUser()).thenReturn(user);
        when(recipeRepository.findByTitleAndUser(
                createRecipeRequestDto.getRecipeName(), user))
                .thenReturn(Optional.of(new Recipe()));

        DuplicateRecipeException duplicateRecipeException = assertThrows(DuplicateRecipeException.class, () -> {
            recipeService.createRecipe(createRecipeRequestDto);
        });
        assertEquals("Already Exists", duplicateRecipeException.getMessage());
        verify(recipeRepository, times(1)).findByTitleAndUser(createRecipeRequestDto.getRecipeName(), user);
        verifyNoMoreInteractions(recipeRepository);
        verifyNoInteractions(userRepository);

    }

    @Test
    void getRecipe_ById_Success(){
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(newRecipe));
        ResponseEntity<Recipe> recipe = recipeService.getRecipe(recipeId);
        assertNotNull(recipe);
        assertEquals(HttpStatus.OK, recipe.getStatusCode());
        assertEquals(newRecipe, recipe.getBody());
        assertEquals(newRecipe.getDescription(), recipe.getBody().getDescription());
    }

    @Test
    void rateRecipe_Success(){

        when(infoGetter.getLoggedInUser()).thenReturn(user);
        when(infoGetter.getRecipeById(recipeId)).thenReturn(newRecipe);
        when(ratingRepository.save(any(Rating.class))).thenReturn(new Rating());

        GetUserDto.RatingDto ratingDto = new GetUserDto.RatingDto("12345", 2);

        ResponseEntity<?> responseEntity = recipeService.rateRecipe(recipeId, ratingDto);


        verify(ratingRepository, times(1)).save(any(Rating.class));
        assertNotNull(ratingDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newRecipe, responseEntity.getBody());
    }


    @Test
    void Test_CommentOnRecipe_Success(){
        when(infoGetter.getLoggedInUser()).thenReturn(user);
        when(infoGetter.getRecipeById(recipeId)).thenReturn(newRecipe);
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        CommentDto commentDto = new CommentDto("I like your comment");

        ResponseEntity<?> responseEntity = recipeService.commentRecipe(recipeId, commentDto);

        verify(commentRepository, times(1)).save(any(Comment.class));
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newRecipe, responseEntity.getBody());

    }

}