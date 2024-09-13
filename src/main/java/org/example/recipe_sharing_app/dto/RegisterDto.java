package org.example.recipe_sharing_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    private String password;

}
