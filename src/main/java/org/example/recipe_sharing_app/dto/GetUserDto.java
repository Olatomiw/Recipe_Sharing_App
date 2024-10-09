package org.example.recipe_sharing_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private String name;
    private String id;
    private String email;
    private List<RecipeDto> recipeDtoList;

    @Data
    @AllArgsConstructor
    public static class CommentDto{
        private String id;
        private String comment;
        private String date;
        private int likes;
        private int dislikes;
    }

    @Builder
    public record RecipeIngredientDto(String name, String quantity){
    }

    @Data
    @AllArgsConstructor
    public static class RecipeDto {

        private String id;
        private String title;
        private String description;
        private String instructions;
        private List<RecipeIngredientDto> recipeIngredientDtoList;
        private List<CommentDto> commentDtoList;
        private List<RatingDto> ratingDtoList;
    }

    @Data
    @AllArgsConstructor
    public static class RatingDto{
        private String id;
        private int rating;
    }
}
