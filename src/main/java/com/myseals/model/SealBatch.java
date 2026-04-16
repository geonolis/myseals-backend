package com.myseals.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_batches")
public class SealBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID batchId;

    @Column(nullable = false)
    private String manufacturer;

    @Column(unique = true, nullable = false, length = 100)
    private String batchNumber;

    @Column(nullable = false, length = 50)
    private String startSealNumber;

    @Column(nullable = false, length = 50)
    private String endSealNumber;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate receivedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by")
    private User registeredBy;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getStartSealNumber() {
        return startSealNumber;
    }

    public void setStartSealNumber(String startSealNumber) {
        this.startSealNumber = startSealNumber;
    }

    public String getEndSealNumber() {
        return endSealNumber;
    }

    public void setEndSealNumber(String endSealNumber) {
        this.endSealNumber = endSealNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public User getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
