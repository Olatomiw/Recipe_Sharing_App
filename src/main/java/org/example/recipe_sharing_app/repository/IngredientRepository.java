package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

   Optional <Ingredient> findByName(String name);
}
