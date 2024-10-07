package org.example.recipe_sharing_app.exception;

import org.springframework.http.HttpStatusCode;

public class EntityNotFoundException extends RuntimeException{

    HttpStatusCode statusCode;

    public EntityNotFoundException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
