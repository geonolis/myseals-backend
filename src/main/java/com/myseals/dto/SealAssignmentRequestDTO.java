package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public UUID getAssignedToOfficeId() { return assignedToOfficeId; }
    public void setAssignedToOfficeId(UUID assignedToOfficeId) { this.assignedToOfficeId = assignedToOfficeId; }
    public UUID getAssignedByUserId() { return assignedByUserId; }
    public void setAssignedByUserId(UUID assignedByUserId) { this.assignedByUserId = assignedByUserId; }
    public OffsetDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(OffsetDateTime returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
