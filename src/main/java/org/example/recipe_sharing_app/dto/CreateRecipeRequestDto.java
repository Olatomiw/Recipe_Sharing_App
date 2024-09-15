package org.example.recipe_sharing_app.dto;

import lombok.Data;

@Data
public class CreateRecipeRequestDto {

    private String recipeName;
    private String description;
    private String instructions;
    private String image;


}
