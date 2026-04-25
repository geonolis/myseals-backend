package com.myseals.dto;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class SealAssignmentResponseDTO {
    private UUID assignmentId;
    private UUID sealId;
    private String sealNumber;
    private UUID assignedToUserId;
    private String assignedToUserName;
    private UUID assignedToOfficeId;
    private String assignedToOfficeName;
    private UUID assignedByUserId;
    private String assignedByUserName;
    private OffsetDateTime assignmentDate;
    private OffsetDateTime returnDate;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
