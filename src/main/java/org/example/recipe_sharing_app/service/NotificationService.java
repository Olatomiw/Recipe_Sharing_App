package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.dto.NotificationDto;
import org.example.recipe_sharing_app.model.Notification;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.springframework.http.ResponseEntity;

public interface NotificationService {

    void createNotification(String message, User user, Recipe recipe);
}
