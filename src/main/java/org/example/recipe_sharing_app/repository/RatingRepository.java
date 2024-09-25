package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, String> {
}
