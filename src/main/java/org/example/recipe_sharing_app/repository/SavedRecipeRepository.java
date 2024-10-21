package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.SavedRecipe;
import org.example.recipe_sharing_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, String> {

    Optional<SavedRecipe> findByUser(User user);
}
