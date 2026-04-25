package com.myseals.service;

import com.myseals.dto.AuditLogResponseDTO;
import com.myseals.model.AuditLog;
import com.myseals.repository.AuditLogRepository;
import com.myseals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public List<AuditLogResponseDTO> findAll() {
        return auditLogRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<AuditLogResponseDTO> findById(@NonNull UUID id) {
        return auditLogRepository.findById(id).map(this::convertToDto);
    }

    // Method to save an audit log (internal use, not exposed via DTO for creation)
    public AuditLog save(@NonNull AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    private AuditLogResponseDTO convertToDto(AuditLog auditLog) {
        AuditLogResponseDTO dto = new AuditLogResponseDTO();
        dto.setAuditId(auditLog.getAuditId());
        dto.setActionType(auditLog.getActionType().name());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId());
        dto.setOldValue(auditLog.getOldValue());
        dto.setNewValue(auditLog.getNewValue());
        dto.setIpAddress(auditLog.getIpAddress());
        dto.setUserAgent(auditLog.getUserAgent());
        dto.setTimestamp(auditLog.getTimestamp());

        if (auditLog.getUser() != null) {
            dto.setUserId(auditLog.getUser().getUserId());
            dto.setUserName(auditLog.getUser().getFullName());
        }
        return dto;
    }
}
