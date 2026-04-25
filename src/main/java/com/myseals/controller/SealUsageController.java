package com.myseals.controller;

import com.myseals.dto.SealUsageRequestDTO;
import com.myseals.dto.SealUsageResponseDTO;
import com.myseals.service.SealUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-usages")
@RequiredArgsConstructor
public class SealUsageController {

    private final SealUsageService sealUsageService;

    @GetMapping
    public ResponseEntity<List<SealUsageResponseDTO>> getAllSealUsages() {
        return ResponseEntity.ok(sealUsageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealUsageResponseDTO> getSealUsageById(@PathVariable @NonNull UUID id) {
        UUID usageId = id;
        return sealUsageService.findById(usageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealUsageResponseDTO> createSealUsage(
            @Valid @RequestBody @NonNull SealUsageRequestDTO sealUsageRequestDTO) {
        SealUsageResponseDTO createdUsage = sealUsageService.createSealUsage(sealUsageRequestDTO);
        return new ResponseEntity<>(createdUsage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealUsage(@PathVariable @NonNull UUID id) {
        UUID usageId = id;
        sealUsageService.deleteById(usageId);
        return ResponseEntity.noContent().build();
    }
}
