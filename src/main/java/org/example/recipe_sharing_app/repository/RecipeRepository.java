package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    Optional<Recipe> findByTitle(String title);

    @Override
    @Query("SELECT r from Recipe r " +
            "LEFT JOIN FETCH r.myRecipeIngredients ri " +
            "LEFT JOIN FETCH ri.ingredient i " +
            "WHERE r.id = :id")
    Optional<Recipe> findById(@Param("id") String id);

    Optional<Recipe> findByTitleAndUser(String title, User user);

    @Query("SELECT p FROM Recipe p " +
            "LEFT JOIN FETCH p.myRecipeIngredients ri " +
            "LEFT JOIN FETCH p.myComments " +
            "LEFT JOIN FETCH p.myRatings " +
            "LEFT JOIN FETCH p.myTags " +
            "LEFT JOIN FETCH p.myNotifications " +
            "WHERE LOWER(p.title)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.user.firstname)  Like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.instructions)  Like LOWER(CONCAT('%', :keyword, '%')) ")
    List<Recipe> searchRecipe(String keyword);
}
