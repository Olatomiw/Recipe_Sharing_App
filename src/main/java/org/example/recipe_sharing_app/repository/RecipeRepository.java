package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    Optional<Recipe> findByTitle(String title);

//    @Override
//    Optional<Recipe> findById(String id);

    Optional<Recipe> findByTitleAndUser(String title, User user);

    @Query("SELECT p FROM Recipe p WHERE " +
            "LOWER(p.title)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.user.firstname)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.instructions)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.myRecipeIngredients)  Like LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> searchRecipe(String keyword);
}
