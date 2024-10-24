package org.example.recipe_sharing_app.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdatePasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
