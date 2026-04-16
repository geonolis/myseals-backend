package com.myseals.controller;

import com.myseals.dto.AuditLogResponseDTO;
import com.myseals.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {
        List<AuditLogResponseDTO> auditLogs = auditLogService.findAll();
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(@PathVariable UUID id) {
        return auditLogService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
