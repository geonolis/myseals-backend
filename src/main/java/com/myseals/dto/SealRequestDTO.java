package com.myseals.dto;

import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SealRequestDTO {
    @NotBlank(message = "Seal number cannot be empty")
    private String sealNumber;

    @NotNull(message = "Batch ID cannot be null")
    private UUID batchId;

    private String currentStatus; // Will be mapped to SealStatus enum

    @NotNull(message = "Current office ID cannot be null")
    private UUID currentOfficeId;

    private UUID assignedToUserId;
}