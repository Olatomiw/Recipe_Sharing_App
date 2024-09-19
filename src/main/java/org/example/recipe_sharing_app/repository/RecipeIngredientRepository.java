package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, String> {
}
