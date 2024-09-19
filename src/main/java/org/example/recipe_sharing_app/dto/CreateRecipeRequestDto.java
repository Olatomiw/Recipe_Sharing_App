package org.example.recipe_sharing_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateRecipeRequestDto {

    private String recipeName;
    private String description;
    private String instructions;
    private String image;
    private List<CreateIngredientDto> ingredients;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateIngredientDto {
        private String id;
        private String ingredientName;
        private String quantity;
    }

}
