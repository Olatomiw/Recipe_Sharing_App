package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String username);
}
