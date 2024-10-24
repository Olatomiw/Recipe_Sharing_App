package org.example.recipe_sharing_app.exception.exceptionHandler;

import org.example.recipe_sharing_app.dto.responseDto.GlobalExceptionResponse;
import org.example.recipe_sharing_app.exception.DuplicateRecipeException;
import org.example.recipe_sharing_app.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRecipeException.class)
    public ResponseEntity<?> handleDuplicateRecipeException(DuplicateRecipeException e) {
        GlobalExceptionResponse exceptionResponse = new GlobalExceptionResponse(
                e.getMessage(), HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        GlobalExceptionResponse exceptionResponse = new GlobalExceptionResponse(
                e.getMessage(), HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
