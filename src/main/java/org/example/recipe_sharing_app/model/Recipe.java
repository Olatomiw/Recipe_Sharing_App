package org.example.recipe_sharing_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="recipe")
    private Set<Comment> myComments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RecipeIngredient> myRecipeIngredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rating> myRatings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private Set<SavedRecipe> mySavedRecipes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_tags",joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name ="tag_id")
    )
    private Set<Tag> myTags;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "recipe")
    private Set <Notification> myNotifications;

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructions='" + instructions + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", image='" + image + '\'' +
                '}';
    }
}
