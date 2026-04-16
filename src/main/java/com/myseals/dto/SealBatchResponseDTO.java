package com.myseals.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SealBatchResponseDTO {
    private UUID batchId;
    private String manufacturer;
    private String batchNumber;
    private String startSealNumber;
    private String endSealNumber;
    private Integer quantity;
    private LocalDate receivedDate;
    private UUID registeredByUserId;
    private String registeredByUserName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters and Setters
    public UUID getBatchId() { return batchId; }
    public void setBatchId(UUID batchId) { this.batchId = batchId; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public String getStartSealNumber() { return startSealNumber; }
    public void setStartSealNumber(String startSealNumber) { this.startSealNumber = startSealNumber; }
    public String getEndSealNumber() { return endSealNumber; }
    public void setEndSealNumber(String endSealNumber) { this.endSealNumber = endSealNumber; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDate getReceivedDate() { return receivedDate; }
    public void setReceivedDate(LocalDate receivedDate) { this.receivedDate = receivedDate; }
    public UUID getRegisteredByUserId() { return registeredByUserId; }
    public void setRegisteredByUserId(UUID registeredByUserId) { this.registeredByUserId = registeredByUserId; }
    public String getRegisteredByUserName() { return registeredByUserName; }
    public void setRegisteredByUserName(String registeredByUserName) { this.registeredByUserName = registeredByUserName; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}