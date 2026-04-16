package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

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

    // Getters and Setters
    public UUID getAssignmentId() { return assignmentId; }
    public void setAssignmentId(UUID assignmentId) { this.assignmentId = assignmentId; }
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public String getAssignedToUserName() { return assignedToUserName; }
    public void setAssignedToUserName(String assignedToUserName) { this.assignedToUserName = assignedToUserName; }
    public UUID getAssignedToOfficeId() { return assignedToOfficeId; }
    public void setAssignedToOfficeId(UUID assignedToOfficeId) { this.assignedToOfficeId = assignedToOfficeId; }
    public String getAssignedToOfficeName() { return assignedToOfficeName; }
    public void setAssignedToOfficeName(String assignedToOfficeName) { this.assignedToOfficeName = assignedToOfficeName; }
    public UUID getAssignedByUserId() { return assignedByUserId; }
    public void setAssignedByUserId(UUID assignedByUserId) { this.assignedByUserId = assignedByUserId; }
    public String getAssignedByUserName() { return assignedByUserName; }
    public void setAssignedByUserName(String assignedByUserName) { this.assignedByUserName = assignedByUserName; }
    public OffsetDateTime getAssignmentDate() { return assignmentDate; }
    public void setAssignmentDate(OffsetDateTime assignmentDate) { this.assignmentDate = assignmentDate; }
    public OffsetDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(OffsetDateTime returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
