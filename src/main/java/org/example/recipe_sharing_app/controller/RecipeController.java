package org.example.recipe_sharing_app.controller;


import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.CreateRecipeRequestDto;
import org.example.recipe_sharing_app.service.RecipeService;
import org.example.recipe_sharing_app.serviceImpl.RecipeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/recipe/")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeRequestDto requestDto){
        return new ResponseEntity<>(recipeService.createRecipe(requestDto), HttpStatus.OK);
    }
}
