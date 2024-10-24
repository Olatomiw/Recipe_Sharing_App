package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT p FROM User p " +
            "LEFT JOIN FETCH p.my_ratings " +
            "LEFT JOIN FETCH p.my_comments " +
            "LEFT JOIN FETCH p.my_recipes ri " +
            "LEFT JOIN FETCH ri.myRecipeIngredients " +
            "LEFT JOIN FETCH ri.myComments " +
            "LEFT JOIN FETCH ri.myRatings " +
            "LEFT JOIN FETCH ri.mySavedRecipes " +
            "LEFT JOIN FETCH ri.myNotifications un " +
            "LEFT JOIN FETCH ri.myTags ut " +
            "LEFT JOIN FETCH p.my_saved_recipes " +
            "LEFT JOIN FETCH p.my_notifications " +
            "WHERE p.email = :email"
    )
    Optional<User> findByEmail(@Param("email") String email);

    @Override
    @Query("SELECT p FROM User p " +
            "LEFT JOIN FETCH p.my_ratings " +
            "LEFT JOIN FETCH p.my_recipes ri " +
            "LEFT JOIN FETCH ri.myRecipeIngredients " +
            "LEFT JOIN FETCH ri.myComments " +
            "WHERE p.id = :id"
    )
    Optional<User>findById(@Param("id") String id);


    Boolean existsByEmail(String username);
}
