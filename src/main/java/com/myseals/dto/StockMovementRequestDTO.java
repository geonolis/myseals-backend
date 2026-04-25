package com.myseals.dto;

import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class StockMovementRequestDTO {
    @NotNull(message = "Seal ID cannot be null")
    private UUID sealId;

    @NotBlank(message = "Movement type cannot be empty")
    private String movementType; // Will be mapped to MovementType enum

    private UUID fromOfficeId;
    private UUID toOfficeId;
    private UUID fromUserId;
    private UUID toUserId;

    @NotNull(message = "Moved by user ID cannot be null")
    private UUID movedByUserId;

    private String notes;
}