package com.myseals.dto;

import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
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
}