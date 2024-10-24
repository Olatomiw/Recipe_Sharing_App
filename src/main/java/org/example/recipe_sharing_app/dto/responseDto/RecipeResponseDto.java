package org.example.recipe_sharing_app.dto.responseDto;

import lombok.Builder;

import java.util.Set;

@Builder
public record RecipeResponseDto(String recipeName, String description,
        String instructions,
        String image, Set<?> ingredients) {

}
