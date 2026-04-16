package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SealRequestDTO {
    @NotBlank(message = "Seal number cannot be empty")
    private String sealNumber;

    @NotNull(message = "Batch ID cannot be null")
    private UUID batchId;

    private String currentStatus; // Will be mapped to SealStatus enum

    @NotNull(message = "Current office ID cannot be null")
    private UUID currentOfficeId;

    private UUID assignedToUserId;

    // Getters and Setters
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getBatchId() { return batchId; }
    public void setBatchId(UUID batchId) { this.batchId = batchId; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public UUID getCurrentOfficeId() { return currentOfficeId; }
    public void setCurrentOfficeId(UUID currentOfficeId) { this.currentOfficeId = currentOfficeId; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
}