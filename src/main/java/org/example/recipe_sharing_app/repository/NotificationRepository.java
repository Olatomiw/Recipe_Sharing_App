package org.example.recipe_sharing_app.repository;

import org.example.recipe_sharing_app.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
