package com.myseals.dto;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID userId;
    private String auth0UserId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private UUID officeId;
    private String officeName;
    private UUID roleId;
    private String roleName;
    private Boolean active;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}