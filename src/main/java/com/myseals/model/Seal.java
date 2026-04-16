package com.myseals.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seals")
public class Seal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sealId;

    @Column(unique = true, nullable = false, length = 50)
    private String sealNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private SealBatch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SealStatus currentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_office_id")
    private Office currentOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedToUser;

    @Column(nullable = false)
    private OffsetDateTime registeredAt;

    @Column(nullable = false)
    private OffsetDateTime lastUpdatedAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = OffsetDateTime.now();
        lastUpdatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getSealId() {
        return sealId;
    }

    public void setSealId(UUID sealId) {
        this.sealId = sealId;
    }

    public String getSealNumber() {
        return sealNumber;
    }

    public void setSealNumber(String sealNumber) {
        this.sealNumber = sealNumber;
    }

    public SealBatch getBatch() {
        return batch;
    }

    public void setBatch(SealBatch batch) {
        this.batch = batch;
    }

    public SealStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(SealStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Office getCurrentOffice() {
        return currentOffice;
    }

    public void setCurrentOffice(Office currentOffice) {
        this.currentOffice = currentOffice;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public OffsetDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(OffsetDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public OffsetDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
