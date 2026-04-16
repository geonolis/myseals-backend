package com.myseals.controller;

import com.myseals.dto.SealBatchRequestDTO;
import com.myseals.dto.SealBatchResponseDTO;
import com.myseals.service.SealBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-batches")
public class SealBatchController {

    @Autowired
    private SealBatchService sealBatchService;

    @GetMapping
    public ResponseEntity<List<SealBatchResponseDTO>> getAllSealBatches() {
        List<SealBatchResponseDTO> sealBatches = sealBatchService.findAll();
        return ResponseEntity.ok(sealBatches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealBatchResponseDTO> getSealBatchById(@PathVariable UUID id) {
        return sealBatchService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealBatchResponseDTO> createSealBatch(@Valid @RequestBody SealBatchRequestDTO sealBatchRequestDTO) {
        SealBatchResponseDTO createdSealBatch = sealBatchService.createSealBatch(sealBatchRequestDTO);
        return new ResponseEntity<>(createdSealBatch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SealBatchResponseDTO> updateSealBatch(@PathVariable UUID id, @Valid @RequestBody SealBatchRequestDTO sealBatchRequestDTO) {
        SealBatchResponseDTO updatedSealBatch = sealBatchService.updateSealBatch(id, sealBatchRequestDTO);
        return ResponseEntity.ok(updatedSealBatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealBatch(@PathVariable UUID id) {
        sealBatchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
