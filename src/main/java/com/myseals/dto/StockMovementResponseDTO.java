package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class StockMovementResponseDTO {
    private UUID movementId;
    private UUID sealId;
    private String sealNumber;
    private String movementType;
    private UUID fromOfficeId;
    private String fromOfficeName;
    private UUID toOfficeId;
    private String toOfficeName;
    private UUID fromUserId;
    private String fromUserName;
    private UUID toUserId;
    private String toUserName;
    private UUID movedByUserId;
    private String movedByUserName;
    private String notes;
    private OffsetDateTime movementDate;
    private OffsetDateTime createdAt;

    // Getters and Setters
    public UUID getMovementId() { return movementId; }
    public void setMovementId(UUID movementId) { this.movementId = movementId; }
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public UUID getFromOfficeId() { return fromOfficeId; }
    public void setFromOfficeId(UUID fromOfficeId) { this.fromOfficeId = fromOfficeId; }
    public String getFromOfficeName() { return fromOfficeName; }
    public void setFromOfficeName(String fromOfficeName) { this.fromOfficeName = fromOfficeName; }
    public UUID getToOfficeId() { return toOfficeId; }
    public void setToOfficeId(UUID toOfficeId) { this.toOfficeId = toOfficeId; }
    public String getToOfficeName() { return toOfficeName; }
    public void setToOfficeName(String toOfficeName) { this.toOfficeName = toOfficeName; }
    public UUID getFromUserId() { return fromUserId; }
    public void setFromUserId(UUID fromUserId) { this.fromUserId = fromUserId; }
    public String getFromUserName() { return fromUserName; }
    public void setFromUserName(String fromUserName) { this.fromUserName = fromUserName; }
    public UUID getToUserId() { return toUserId; }
    public void setToUserId(UUID toUserId) { this.toUserId = toUserId; }
    public String getToUserName() { return toUserName; }
    public void setToUserName(String toUserName) { this.toUserName = toUserName; }
    public UUID getMovedByUserId() { return movedByUserId; }
    public void setMovedByUserId(UUID movedByUserId) { this.movedByUserId = movedByUserId; }
    public String getMovedByUserName() { return movedByUserName; }
    public void setMovedByUserName(String movedByUserName) { this.movedByUserName = movedByUserName; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public OffsetDateTime getMovementDate() { return movementDate; }
    public void setMovementDate(OffsetDateTime movementDate) { this.movementDate = movementDate; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}