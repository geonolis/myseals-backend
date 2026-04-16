package com.myseals.dto;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public UUID getUsedByUserId() { return usedByUserId; }
    public void setUsedByUserId(UUID usedByUserId) { this.usedByUserId = usedByUserId; }
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
}