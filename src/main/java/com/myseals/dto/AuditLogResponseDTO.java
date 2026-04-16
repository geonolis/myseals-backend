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