package com.myseals.dto;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SealBatchRequestDTO {
    @NotBlank(message = "Manufacturer cannot be empty")
    private String manufacturer;

    @NotBlank(message = "Batch number cannot be empty")
    private String batchNumber;

    @NotBlank(message = "Start seal number cannot be empty")
    private String startSealNumber;

    @NotBlank(message = "End seal number cannot be empty")
    private String endSealNumber;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Received date cannot be null")
    private LocalDate receivedDate;

    private UUID registeredByUserId;

    // Getters and Setters
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
}