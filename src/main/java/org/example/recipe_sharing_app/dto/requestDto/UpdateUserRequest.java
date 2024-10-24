package org.example.recipe_sharing_app.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String firstname;
    private String lastname;
    private String email;
}
