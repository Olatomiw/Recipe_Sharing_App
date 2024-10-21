package org.example.recipe_sharing_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "recipes")
public class Recipe {

    @Id
    private String id;

    @Column(name = "recipe_name", nullable = false)
    private String title;

    private String description;

    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @Column(name = "created_at",nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "img_url")
    private String image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy ="recipe")
    private List<Comment> myComments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeIngredient> myRecipeIngredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> myRatings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<SavedRecipe> mySavedRecipes;

    @ManyToMany
    @JoinTable(
            name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name ="tag_id")
    )
    private List<Tag> myTags;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "recipe")
    private List <Notification> myNotifications;

}
