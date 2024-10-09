package org.example.recipe_sharing_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Builder
@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "users")
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    @NotNull(message = "Field cannot be null")
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
//    @Size(min = 8, max = 20)
    private String password;

    @JsonIgnore
    private Boolean is_verified = false;

    @JsonIgnore
    private Boolean deleted = false;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Comment> my_comments;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List <Recipe> my_recipes;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> my_ratings;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SavedRecipe> my_saved_recipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> my_notifications;



}
