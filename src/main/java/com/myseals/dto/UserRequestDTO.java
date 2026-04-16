package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDTO {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    private String phoneNumber;

    @NotNull(message = "Office ID cannot be null")
    private UUID officeId;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;

    private Boolean isActive = true; // Default to active

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public UUID getOfficeId() { return officeId; }
    public void setOfficeId(UUID officeId) { this.officeId = officeId; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
}