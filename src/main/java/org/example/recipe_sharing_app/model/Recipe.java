package org.example.recipe_sharing_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
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
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @Column(name = "created_at",nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "img_url")
    private String image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="recipe")
    private List<Comment> myComments;

}
