package org.example.recipe_sharing_app.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.recipe_sharing_app.dto.NotificationDto;
import org.example.recipe_sharing_app.model.Notification;
import org.example.recipe_sharing_app.model.Recipe;
import org.example.recipe_sharing_app.model.User;
import org.example.recipe_sharing_app.repository.NotificationRepository;
import org.example.recipe_sharing_app.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(String message, User user, Recipe recipe) {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .message(message)
                .user(user)
                .recipe(recipe)
                .build();
        notificationRepository.save(notification);
    }
}
