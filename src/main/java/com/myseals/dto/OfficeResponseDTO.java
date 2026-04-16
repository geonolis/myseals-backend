package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class OfficeResponseDTO {
    private UUID officeId;
    private String officeName;
    private String officeCode;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private UUID parentOfficeId;
    private String parentOfficeName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters and Setters
    public UUID getOfficeId() { return officeId; }
    public void setOfficeId(UUID officeId) { this.officeId = officeId; }
    public String getOfficeName() { return officeName; }
    public void setOfficeName(String officeName) { this.officeName = officeName; }
    public String getOfficeCode() { return officeCode; }
    public void setOfficeCode(String officeCode) { this.officeCode = officeCode; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public UUID getParentOfficeId() { return parentOfficeId; }
    public void setParentOfficeId(UUID parentOfficeId) { this.parentOfficeId = parentOfficeId; }
    public String getParentOfficeName() { return parentOfficeName; }
    public void setParentOfficeName(String parentOfficeName) { this.parentOfficeName = parentOfficeName; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}