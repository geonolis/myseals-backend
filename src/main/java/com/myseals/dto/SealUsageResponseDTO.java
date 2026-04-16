package com.myseals.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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

    // Getters and Setters
    public UUID getUsageId() { return usageId; }
    public void setUsageId(UUID usageId) { this.usageId = usageId; }
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getUsedByUserId() { return usedByUserId; }
    public void setUsedByUserId(UUID usedByUserId) { this.usedByUserId = usedByUserId; }
    public String getUsedByUserName() { return usedByUserName; }
    public void setUsedByUserName(String usedByUserName) { this.usedByUserName = usedByUserName; }
    public String getUsageLocation() { return usageLocation; }
    public void setUsageLocation(String usageLocation) { this.usageLocation = usageLocation; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public String getDocumentReference() { return documentReference; }
    public void setDocumentReference(String documentReference) { this.documentReference = documentReference; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public OffsetDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(OffsetDateTime usedAt) { this.usedAt = usedAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}