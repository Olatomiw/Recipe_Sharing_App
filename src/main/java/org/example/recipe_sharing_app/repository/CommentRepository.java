package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
