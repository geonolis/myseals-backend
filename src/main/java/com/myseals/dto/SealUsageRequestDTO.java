package com.myseals.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SealUsageRequestDTO {
    @NotNull(message = "Seal ID cannot be null")
    private UUID sealId;

    @NotNull(message = "Used by user ID cannot be null")
    private UUID usedByUserId;

    @NotBlank(message = "Usage location cannot be empty")
    private String usageLocation;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String documentReference;
    private String photoUrl; // URL to image stored in Supabase Storage or S3
    private String notes;
}