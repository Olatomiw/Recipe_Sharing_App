package org.example.recipe_sharing_app.exception;

public class DuplicateRecipeException extends RuntimeException {

    Integer Status;
    public DuplicateRecipeException(String message, Integer status) {
        super(message);
        this.Status = status;
    }
}
