package org.example.recipe_sharing_app.controller;


import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.requestDto.CommentDto;
import org.example.recipe_sharing_app.dto.requestDto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.dto.requestDto.GetUserDto;
import org.example.recipe_sharing_app.dto.responseDto.RecipeResponseDto;
import org.example.recipe_sharing_app.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/recipe/")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeRequestDto requestDto){
        return new ResponseEntity<>(recipeService.createRecipe(requestDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable String id){
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchRecipes(@RequestParam String keyword){
        List<RecipeResponseDto>recipes = recipeService.searchRecipe(keyword);
        if (recipes.isEmpty()){
            return new ResponseEntity<>("No result",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<?> rateRecipe(@PathVariable String id, @RequestBody GetUserDto.RatingDto rating){
        return recipeService.rateRecipe(id,rating );
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> commentRecipe(@PathVariable String id, @RequestBody CommentDto commentDto){
        return recipeService.commentRecipe(id,commentDto);
    }

    @PostMapping("/saved/{id}")
    public ResponseEntity<?> addToSavedRecipe(@PathVariable String id){
        return recipeService.saveRecipe(id);
    }

    @GetMapping("/savedRecipe")
    public ResponseEntity<?> getSavedRecipe(){
        return recipeService.getSavedRecipe();
    }
}
