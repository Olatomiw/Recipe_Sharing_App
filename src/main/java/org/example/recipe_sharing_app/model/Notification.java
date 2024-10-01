package org.example.recipe_sharing_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "notifications")
@Entity
public class Notification {

    @Id
    private String id;
    private String message;
    @CurrentTimestamp
    private LocalDateTime createdAt;
    private boolean isRead = false;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false) @JsonIgnore
    private Recipe recipe;
}
