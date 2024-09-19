package org.example.recipe_sharing_app.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class UpdateUserRequest {

    private String firstname;
    private String lastname;
    private String email;
}
