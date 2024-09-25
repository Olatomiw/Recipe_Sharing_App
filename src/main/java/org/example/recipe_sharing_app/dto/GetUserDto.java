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

    @Builder
    public static class getCommentDto{
        private String id;
        private String comment;
        private String date;
        private int likes;
        private int dislikes;
    }

    @Builder
    public static class RecipeIngredientDto{
        private String id;
        private String name;
    }

    @Builder
    public static class RecipeDto {
        private String name;
        private String id;
        private String title;
        private String description;
        private List<getCommentDto> commentDtoList;
        private List<RatingDto> ratingDtoList;
        private List<RecipeIngredientDto> recipeIngredientDtoList;
    }

    @Builder
    @Data
    public static class RatingDto{
        private String id;
        private int rating;
    }
}
