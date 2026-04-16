package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public UUID getFromOfficeId() { return fromOfficeId; }
    public void setFromOfficeId(UUID fromOfficeId) { this.fromOfficeId = fromOfficeId; }
    public UUID getToOfficeId() { return toOfficeId; }
    public void setToOfficeId(UUID toOfficeId) { this.toOfficeId = toOfficeId; }
    public UUID getFromUserId() { return fromUserId; }
    public void setFromUserId(UUID fromUserId) { this.fromUserId = fromUserId; }
    public UUID getToUserId() { return toUserId; }
    public void setToUserId(UUID toUserId) { this.toUserId = toUserId; }
    public UUID getMovedByUserId() { return movedByUserId; }
    public void setMovedByUserId(UUID movedByUserId) { this.movedByUserId = movedByUserId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}