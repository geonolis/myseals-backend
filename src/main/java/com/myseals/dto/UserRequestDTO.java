package com.myseals.dto;

import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Auth0 User ID cannot be empty")
    private String auth0UserId;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    private String phoneNumber;

    @NotNull(message = "Office ID cannot be null")
    private UUID officeId;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;

    private Boolean active = true;
}