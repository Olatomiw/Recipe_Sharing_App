package org.example.recipe_sharing_app.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;

}
