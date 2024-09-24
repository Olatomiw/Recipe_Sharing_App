package org.example.recipe_sharing_app.controller;


import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.model.Recipe;
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
        List<Recipe>recipes = recipeService.searchRecipe(keyword);
        if (recipes.isEmpty()){
            return new ResponseEntity<>("No result",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}
