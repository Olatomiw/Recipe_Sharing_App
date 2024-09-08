package org.example.recipe_sharing_app.model;

import jakarta.persistence.*;

@Entity
@Table(name="ratings")
public class Rating {

    @Id
    private String id;

    @Column(updatable = true)
    private int rating = 0;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
