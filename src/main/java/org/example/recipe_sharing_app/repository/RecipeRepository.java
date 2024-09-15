package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    Optional<Recipe> findByTitle(String title);
}
