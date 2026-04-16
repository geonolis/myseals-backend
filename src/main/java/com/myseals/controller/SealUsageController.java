package com.myseals.controller;

import com.myseals.dto.SealUsageRequestDTO;
import com.myseals.dto.SealUsageResponseDTO;
import com.myseals.service.SealUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-usage")
public class SealUsageController {

    @Autowired
    private SealUsageService sealUsageService;

    @GetMapping
    public ResponseEntity<List<SealUsageResponseDTO>> getAllSealUsage() {
        List<SealUsageResponseDTO> sealUsageRecords = sealUsageService.findAll();
        return ResponseEntity.ok(sealUsageRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealUsageResponseDTO> getSealUsageById(@PathVariable UUID id) {
        return sealUsageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealUsageResponseDTO> createSealUsage(@Valid @RequestBody SealUsageRequestDTO sealUsageRequestDTO) {
        SealUsageResponseDTO createdSealUsage = sealUsageService.createSealUsage(sealUsageRequestDTO);
        return new ResponseEntity<>(createdSealUsage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealUsage(@PathVariable UUID id) {
        sealUsageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
