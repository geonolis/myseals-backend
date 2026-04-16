package com.myseals.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_usage")
public class SealUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID usageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seal_id", nullable = false)
    private Seal seal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_by")
    private User usedBy;

    @Column(nullable = false)
    private String usageLocation;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String documentReference;
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private OffsetDateTime usedAt;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        usedAt = OffsetDateTime.now();
        createdAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getUsageId() {
        return usageId;
    }

    public void setUsageId(UUID usageId) {
        this.usageId = usageId;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public User getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(User usedBy) {
        this.usedBy = usedBy;
    }

    public String getUsageLocation() {
        return usageLocation;
    }

    public void setUsageLocation(String usageLocation) {
        this.usageLocation = usageLocation;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(OffsetDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
