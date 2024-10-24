package org.example.recipe_sharing_app.service;

import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;

public interface NotificationService {

    void createNotification(String message, User user, Recipe recipe);
}
