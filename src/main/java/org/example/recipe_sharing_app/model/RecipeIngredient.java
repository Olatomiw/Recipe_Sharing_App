package org.example.recipe_sharing_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "recipe_id",nullable = false)
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private String quantity;


    public Ingredient getIngredient(Ingredient ingredient) {
        return this.ingredient;
    }
}
