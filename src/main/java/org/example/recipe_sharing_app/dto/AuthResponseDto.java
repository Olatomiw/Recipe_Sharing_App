package org.example.recipe_sharing_app.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDto {
    private String token;
    private String username;
}
