package com.myseals.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class SealUsageResponseDTO {
    private UUID usageId;
    private UUID sealId;
    private String sealNumber;
    private UUID usedByUserId;
    private String usedByUserName;
    private String usageLocation;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String documentReference;
    private String photoUrl;
    private String notes;
    private OffsetDateTime usedAt;
    private OffsetDateTime createdAt;
}