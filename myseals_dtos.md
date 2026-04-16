# mySeals Application: Data Transfer Objects (DTOs) and Example JSONs

This document defines the Data Transfer Objects (DTOs) that will be used for communication between the mySeals Spring Boot backend and its client applications (Web and Android). Each DTO includes its Java class definition and an example JSON representation for both request (POST) and response (GET) operations.

## 1. User DTOs

### 1.1. `UserRequestDTO` (For creating/updating users)

```java
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
```

**Example JSON (POST /api/v1/users):**

```json
{
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890",
  "officeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "roleId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "isActive": true
}
```

### 1.2. `UserResponseDTO` (For retrieving users)

```java
package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserResponseDTO {
    private UUID userId;
    private String auth0UserId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private UUID officeId;
    private String officeName;
    private UUID roleId;
    private String roleName;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters and Setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getAuth0UserId() { return auth0UserId; }
    public void setAuth0UserId(String auth0UserId) { this.auth0UserId = auth0UserId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public UUID getOfficeId() { return officeId; }
    public void setOfficeId(UUID officeId) { this.officeId = officeId; }
    public String getOfficeName() { return officeName; }
    public void setOfficeName(String officeName) { this.officeName = officeName; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
```

**Example JSON (GET /api/v1/users/{id}):**

```json
{
  "userId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "auth0UserId": "auth0|60c72b2f1234567890abcdef",
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890",
  "officeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "officeName": "Headquarters Office",
  "roleId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "roleName": "OFFICE_MANAGER",
  "isActive": true,
  "createdAt": "2023-10-26T10:00:00Z",
  "updatedAt": "2023-10-26T10:00:00Z"
}
```

## 2. Seal DTOs

### 2.1. `SealRequestDTO` (For creating/updating seals)

```java
package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SealRequestDTO {
    @NotBlank(message = "Seal number cannot be empty")
    private String sealNumber;

    @NotNull(message = "Batch ID cannot be null")
    private UUID batchId;

    private String currentStatus; // Will be mapped to SealStatus enum

    @NotNull(message = "Current office ID cannot be null")
    private UUID currentOfficeId;

    private UUID assignedToUserId;

    // Getters and Setters
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getBatchId() { return batchId; }
    public void setBatchId(UUID batchId) { this.batchId = batchId; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public UUID getCurrentOfficeId() { return currentOfficeId; }
    public void setCurrentOfficeId(UUID currentOfficeId) { this.currentOfficeId = currentOfficeId; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
}
```

**Example JSON (POST /api/v1/seals):**

```json
{
  "sealNumber": "MS-000001",
  "batchId": "d1e2f3a4-b5c6-7890-1234-567890abcdef",
  "currentStatus": "IN_STOCK",
  "currentOfficeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "assignedToUserId": null
}
```

### 2.2. `SealResponseDTO` (For retrieving seals)

```java
package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SealResponseDTO {
    private UUID sealId;
    private String sealNumber;
    private UUID batchId;
    private String batchNumber;
    private String manufacturer;
    private String currentStatus;
    private UUID currentOfficeId;
    private String currentOfficeName;
    private UUID assignedToUserId;
    private String assignedToUserName;
    private OffsetDateTime registeredAt;
    private OffsetDateTime lastUpdatedAt;

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getBatchId() { return batchId; }
    public void setBatchId(UUID batchId) { this.batchId = batchId; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public UUID getCurrentOfficeId() { return currentOfficeId; }
    public void setCurrentOfficeId(UUID currentOfficeId) { this.currentOfficeId = currentOfficeId; }
    public String getCurrentOfficeName() { return currentOfficeName; }
    public void setCurrentOfficeName(String currentOfficeName) { this.currentOfficeName = currentOfficeName; }
    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public String getAssignedToUserName() { return assignedToUserName; }
    public void setAssignedToUserName(String assignedToUserName) { this.assignedToUserName = assignedToUserName; }
    public OffsetDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(OffsetDateTime registeredAt) { this.registeredAt = registeredAt; }
    public OffsetDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}
```

**Example JSON (GET /api/v1/seals/{id}):**

```json
{
  "sealId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "sealNumber": "MS-000001",
  "batchId": "d1e2f3a4-b5c6-7890-1234-567890abcdef",
  "batchNumber": "BATCH-XYZ-2023",
  "manufacturer": "SealCo Inc.",
  "currentStatus": "IN_STOCK",
  "currentOfficeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "currentOfficeName": "Headquarters Office",
  "assignedToUserId": null,
  "assignedToUserName": null,
  "registeredAt": "2023-10-26T10:05:00Z",
  "lastUpdatedAt": "2023-10-26T10:05:00Z"
}
```

## 3. Seal Batch DTOs

### 3.1. `SealBatchRequestDTO` (For creating/updating seal batches)

```java
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
```

**Example JSON (POST /api/v1/seal-batches):**

```json
{
  "manufacturer": "SealCo Inc.",
  "batchNumber": "BATCH-XYZ-2023",
  "startSealNumber": "MS-000001",
  "endSealNumber": "MS-001000",
  "quantity": 1000,
  "receivedDate": "2023-10-25",
  "registeredByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef"
}
```

### 3.2. `SealBatchResponseDTO` (For retrieving seal batches)

```java
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
```

**Example JSON (GET /api/v1/seal-batches/{id}):**

```json
{
  "batchId": "d1e2f3a4-b5c6-7890-1234-567890abcdef",
  "manufacturer": "SealCo Inc.",
  "batchNumber": "BATCH-XYZ-2023",
  "startSealNumber": "MS-000001",
  "endSealNumber": "MS-001000",
  "quantity": 1000,
  "receivedDate": "2023-10-25",
  "registeredByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "registeredByUserName": "John Doe",
  "createdAt": "2023-10-25T09:00:00Z",
  "updatedAt": "2023-10-25T09:00:00Z"
}
```

## 4. Role DTOs

### 4.1. `RoleResponseDTO` (For retrieving roles)

```java
package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RoleResponseDTO {
    private UUID roleId;
    private String roleName;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters and Setters
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
```

**Example JSON (GET /api/v1/roles):**

```json
[
  {
    "roleId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "roleName": "HQ_ADMIN",
    "description": "Headquarters Administrator with full system access.",
    "createdAt": "2023-10-20T08:00:00Z",
    "updatedAt": "2023-10-20T08:00:00Z"
  },
  {
    "roleId": "g1h2i3j4-k5l6-7890-5432-10fedcba9876",
    "roleName": "OFFICE_MANAGER",
    "description": "Manages seals and users within a specific customs office.",
    "createdAt": "2023-10-20T08:00:00Z",
    "updatedAt": "2023-10-20T08:00:00Z"
  }
]
```

## 5. Office DTOs

### 5.1. `OfficeRequestDTO` (For creating/updating offices)

```java
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
```

**Example JSON (POST /api/v1/offices):**

```json
{
  "officeName": "Headquarters Office",
  "officeCode": "HQ001",
  "address": "123 Main St, City, Country",
  "contactEmail": "hq@example.com",
  "contactPhone": "+1122334455",
  "parentOfficeId": null
}
```

### 5.2. `OfficeResponseDTO` (For retrieving offices)

```java
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
```

**Example JSON (GET /api/v1/offices/{id}):**

```json
{
  "officeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "officeName": "Headquarters Office",
  "officeCode": "HQ001",
  "address": "123 Main St, City, Country",
  "contactEmail": "hq@example.com",
  "contactPhone": "+1122334455",
  "parentOfficeId": null,
  "parentOfficeName": null,
  "createdAt": "2023-10-26T09:30:00Z",
  "updatedAt": "2023-10-26T09:30:00Z"
}
```

## 6. Seal Usage DTOs

### 6.1. `SealUsageRequestDTO` (For creating seal usage records)

```java
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
```

**Example JSON (POST /api/v1/seal-usage):**

```json
{
  "sealId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "usedByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "usageLocation": "Port of Entry X, Container Yard Y",
  "latitude": 34.0522,
  "longitude": -118.2437,
  "documentReference": "BL-123456789",
  "photoUrl": "https://supabase.co/storage/v1/object/public/myseals/seal_photos/seal_usage_1.jpg",
  "notes": "Seal applied to container ABC-123 for transit to destination Z."
}
```

### 6.2. `SealUsageResponseDTO` (For retrieving seal usage records)

```java
package com.myseals.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SealUsageResponseDTO {
    private UUID usageId;
    private UUID sealId;
    private String sealNumber;
    private UUID usedByUserId;
    private String usedByUserName;
    private String usageLocation;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String documentReference;
    private String photoUrl;
    private String notes;
    private OffsetDateTime usedAt;
    private OffsetDateTime createdAt;

    // Getters and Setters
    public UUID getUsageId() { return usageId; }
    public void setUsageId(UUID usageId) { this.usageId = usageId; }
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public UUID getUsedByUserId() { return usedByUserId; }
    public void setUsedByUserId(UUID usedByUserId) { this.usedByUserId = usedByUserId; }
    public String getUsedByUserName() { return usedByUserName; }
    public void setUsedByUserName(String usedByUserName) { this.usedByUserName = usedByUserName; }
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
    public OffsetDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(OffsetDateTime usedAt) { this.usedAt = usedAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
```

**Example JSON (GET /api/v1/seal-usage/{id}):**

```json
{
  "usageId": "f1g2h3i4-j5k6-7890-1234-567890abcdef",
  "sealId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "sealNumber": "MS-000001",
  "usedByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "usedByUserName": "John Doe",
  "usageLocation": "Port of Entry X, Container Yard Y",
  "latitude": 34.0522,
  "longitude": -118.2437,
  "documentReference": "BL-123456789",
  "photoUrl": "https://supabase.co/storage/v1/object/public/myseals/seal_photos/seal_usage_1.jpg",
  "notes": "Seal applied to container ABC-123 for transit to destination Z.",
  "usedAt": "2023-10-26T11:00:00Z",
  "createdAt": "2023-10-26T11:00:00Z"
}
```

## 7. Stock Movement DTOs

### 7.1. `StockMovementRequestDTO` (For creating stock movements)

```java
package com.myseals.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StockMovementRequestDTO {
    @NotNull(message = "Seal ID cannot be null")
    private UUID sealId;

    @NotBlank(message = "Movement type cannot be empty")
    private String movementType; // Will be mapped to MovementType enum

    private UUID fromOfficeId;
    private UUID toOfficeId;
    private UUID fromUserId;
    private UUID toUserId;

    @NotNull(message = "Moved by user ID cannot be null")
    private UUID movedByUserId;

    private String notes;

    // Getters and Setters
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public UUID getFromOfficeId() { return fromOfficeId; }
    public void setFromOfficeId(UUID fromOfficeId) { this.fromOfficeId = fromOfficeId; }
    public UUID getToOfficeId() { return toOfficeId; }
    public void setToOfficeId(UUID toOfficeId) { this.toOfficeId = toOfficeId; }
    public UUID getFromUserId() { return fromUserId; }
    public void setFromUserId(UUID fromUserId) { this.fromUserId = fromUserId; }
    public UUID getToUserId() { return toUserId; }
    public void setToUserId(UUID toUserId) { this.toUserId = toUserId; }
    public UUID getMovedByUserId() { return movedByUserId; }
    public void setMovedByUserId(UUID movedByUserId) { this.movedByUserId = movedByUserId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
```

**Example JSON (POST /api/v1/stock-movements):**

```json
{
  "sealId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "movementType": "TRANSFER_OUT",
  "fromOfficeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "toOfficeId": "b2c3d4e5-f6a7-8901-2345-67890abcdef0",
  "fromUserId": null,
  "toUserId": null,
  "movedByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "notes": "Transferring 100 seals to Branch Office B."
}
```

### 7.2. `StockMovementResponseDTO` (For retrieving stock movements)

```java
package com.myseals.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class StockMovementResponseDTO {
    private UUID movementId;
    private UUID sealId;
    private String sealNumber;
    private String movementType;
    private UUID fromOfficeId;
    private String fromOfficeName;
    private UUID toOfficeId;
    private String toOfficeName;
    private UUID fromUserId;
    private String fromUserName;
    private UUID toUserId;
    private String toUserName;
    private UUID movedByUserId;
    private String movedByUserName;
    private String notes;
    private OffsetDateTime movementDate;
    private OffsetDateTime createdAt;

    // Getters and Setters
    public UUID getMovementId() { return movementId; }
    public void setMovementId(UUID movementId) { this.movementId = movementId; }
    public UUID getSealId() { return sealId; }
    public void setSealId(UUID sealId) { this.sealId = sealId; }
    public String getSealNumber() { return sealNumber; }
    public void setSealNumber(String sealNumber) { this.sealNumber = sealNumber; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public UUID getFromOfficeId() { return fromOfficeId; }
    public void setFromOfficeId(UUID fromOfficeId) { this.fromOfficeId = fromOfficeId; }
    public String getFromOfficeName() { return fromOfficeName; }
    public void setFromOfficeName(String fromOfficeName) { this.fromOfficeName = fromOfficeName; }
    public UUID getToOfficeId() { return toOfficeId; }
    public void setToOfficeId(UUID toOfficeId) { this.toOfficeId = toOfficeId; }
    public String getToOfficeName() { return toOfficeName; }
    public void setToOfficeName(String toOfficeName) { this.toOfficeName = toOfficeName; }
    public UUID getFromUserId() { return fromUserId; }
    public void setFromUserId(UUID fromUserId) { this.fromUserId = fromUserId; }
    public String getFromUserName() { return fromUserName; }
    public void setFromUserName(String fromUserName) { this.fromUserName = fromUserName; }
    public UUID getToUserId() { return toUserId; }
    public void setToUserId(UUID toUserId) { this.toUserId = toUserId; }
    public String getToUserName() { return toUserName; }
    public void setToUserName(String toUserName) { this.toUserName = toUserName; }
    public UUID getMovedByUserId() { return movedByUserId; }
    public void setMovedByUserId(UUID movedByUserId) { this.movedByUserId = movedByUserId; }
    public String getMovedByUserName() { return movedByUserName; }
    public void setMovedByUserName(String movedByUserName) { this.movedByUserName = movedByUserName; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public OffsetDateTime getMovementDate() { return movementDate; }
    public void setMovementDate(OffsetDateTime movementDate) { this.movementDate = movementDate; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
```

**Example JSON (GET /api/v1/stock-movements/{id}):**

```json
{
  "movementId": "g1h2i3j4-k5l6-7890-1234-567890abcdef",
  "sealId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "sealNumber": "MS-000001",
  "movementType": "TRANSFER_OUT",
  "fromOfficeId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "fromOfficeName": "Headquarters Office",
  "toOfficeId": "b2c3d4e5-f6a7-8901-2345-67890abcdef0",
  "toOfficeName": "Branch Office B",
  "fromUserId": null,
  "fromUserName": null,
  "toUserId": null,
  "toUserName": null,
  "movedByUserId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "movedByUserName": "John Doe",
  "notes": "Transferring 100 seals to Branch Office B.",
  "movementDate": "2023-10-26T12:00:00Z",
  "createdAt": "2023-10-26T12:00:00Z"
}
```

## 8. Audit Log DTOs

### 8.1. `AuditLogResponseDTO` (For retrieving audit logs)

```java
package com.myseals.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AuditLogResponseDTO {
    private UUID auditId;
    private UUID userId;
    private String userName;
    private String actionType;
    private String entityType;
    private UUID entityId;
    private JsonNode oldValue;
    private JsonNode newValue;
    private String ipAddress;
    private String userAgent;
    private OffsetDateTime timestamp;

    // Getters and Setters
    public UUID getAuditId() { return auditId; }
    public void setAuditId(UUID auditId) { this.auditId = auditId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public UUID getEntityId() { return entityId; }
    public void setEntityId(UUID entityId) { this.entityId = entityId; }
    public JsonNode getOldValue() { return oldValue; }
    public void setOldValue(JsonNode oldValue) { this.oldValue = oldValue; }
    public JsonNode getNewValue() { return newValue; }
    public void setNewValue(JsonNode newValue) { this.newValue = newValue; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
```

**Example JSON (GET /api/v1/audit-logs/{id}):**

```json
{
  "auditId": "h1i2j3k4-l5m6-7890-1234-567890abcdef",
  "userId": "c1d2e3f4-a5b6-7890-1234-567890abcdef",
  "userName": "John Doe",
  "actionType": "CREATE",
  "entityType": "seal",
  "entityId": "e1f2g3h4-i5j6-7890-1234-567890abcdef",
  "oldValue": null,
  "newValue": {
    "sealNumber": "MS-000001",
    "currentStatus": "REGISTERED"
  },
  "ipAddress": "192.168.1.100",
  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36",
  "timestamp": "2023-10-26T12:30:00Z"
}
```
