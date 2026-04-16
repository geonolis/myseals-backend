package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SealResponseDTO {
    private UUID sealId;
    private String sealNumber;
    private UUID batchId;
    private String batchNumber;
    private String manufacturer;
    private String currentStatus;
    private UUID currentOfficeId;
    private String currentOfficeName;
    private UUID assignedToUserId;
    private String assignedToUserName;
    private OffsetDateTime registeredAt;
    private OffsetDateTime lastUpdatedAt;

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getBatchId() { return batchId; }
    public void setBatchId(UUID batchId) { this.batchId = batchId; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public UUID getCurrentOfficeId() { return currentOfficeId; }
    public void setCurrentOfficeId(UUID currentOfficeId) { this.currentOfficeId = currentOfficeId; }
    public String getCurrentOfficeName() { return currentOfficeName; }
    public void setCurrentOfficeName(String currentOfficeName) { this.currentOfficeName = currentOfficeName; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public String getAssignedToUserName() { return assignedToUserName; }
    public void setAssignedToUserName(String assignedToUserName) { this.assignedToUserName = assignedToUserName; }
    public OffsetDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(OffsetDateTime registeredAt) { this.registeredAt = registeredAt; }
    public OffsetDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}