package com.ez2pay.business.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "User Credentials")
public class UserDTO {
    @Schema(description = "Username", example = "hiep.ho", required = true)
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @Schema(description = "Password", example = "admin123", required = true)
    private String password;

    @Schema(description = "Full name", example = "Ho Minh Hiep")
    private String fullName;

    @Schema(description = "Token assigned if authenticated", example = "")
    private String token;
}
