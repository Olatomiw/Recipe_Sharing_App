package org.example.recipe_sharing_app.dto.responseDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.example.recipe_sharing_app.dto.requestDto.CommentDto;
import org.example.recipe_sharing_app.dto.requestDto.NotificationDto;
import org.example.recipe_sharing_app.model.Rating;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.SavedRecipe;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link org.example.recipe_sharing_app.model.User}
 */
public record UserDto(String id, @NotNull(message = "Field cannot be null") String firstname, String lastname,
                      @Email String email, Set<CommentDto> my_comments, Set<Recipe> my_recipes, Set<Rating> my_ratings,
                      Set<SavedRecipe> my_saved_recipes,
                      Set<NotificationDto> my_notifications) implements Serializable {
}