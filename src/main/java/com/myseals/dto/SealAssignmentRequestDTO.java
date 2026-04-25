package com.myseals.dto;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SealAssignmentRequestDTO {
    @NotNull(message = "Seal ID cannot be null")
    private UUID sealId;

    private UUID assignedToUserId;
    private UUID assignedToOfficeId;

    @NotNull(message = "Assigned by user ID cannot be null")
    private UUID assignedByUserId;

    private OffsetDateTime returnDate;

    @NotBlank(message = "Status cannot be empty")
    private String status; // e.g., ACTIVE, RETURNED, CANCELLED
}
