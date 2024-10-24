package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecipeRepositoryTests {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    private User user = new User();
    private Recipe recipe;
    private Recipe recipe2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID().toString())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();
        recipe = Recipe.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .title("John Doe")
                .description("John Doe recipe")
                .instructions("cook for 2hrs")
                .image("image/url")
                .createdAt(LocalDateTime.now())
                .build();
        recipe2 = Recipe.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .title("John Does")
                .description("John Doe recipe")
                .instructions("cook for 2hrs")
                .image("image/url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void RecipeRepository_Save_ReturnsSavedRecipe() {
        userRepository.save(user);
       Recipe savedRecipe = recipeRepository.save(recipe);

       assertThat(savedRecipe).isNotNull();
       assertThat(savedRecipe.getId()).isEqualTo(recipe.getId());
       assertThat(savedRecipe.getId()).isGreaterThan("0");
    }

    @Test
    public void RecipeRepository_FindAll_ReturnsSavedRecipe() {
//        userRepository.save(user);
        recipeRepository.saveAll(Arrays.asList(recipe,recipe2));
        List<Recipe> savedRecipes = recipeRepository.findAll();

        assertThat(savedRecipes.size()).isGreaterThan(0);
        assertThat(savedRecipes.size()).isGreaterThan(2);

    }

}
