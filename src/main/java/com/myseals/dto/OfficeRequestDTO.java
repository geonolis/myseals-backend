package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OfficeRequestDTO {
    @NotBlank(message = "Office name cannot be empty")
    private String officeName;

    @NotBlank(message = "Office code cannot be empty")
    private String officeCode;

    private String address;

    @Email(message = "Contact email should be valid")
    private String contactEmail;

    private String contactPhone;

    private UUID parentOfficeId;

    // Getters and Setters
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
}