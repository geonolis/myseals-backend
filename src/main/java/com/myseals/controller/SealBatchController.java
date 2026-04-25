package com.myseals.controller;

import com.myseals.dto.SealBatchRequestDTO;
import com.myseals.dto.SealBatchResponseDTO;
import com.myseals.service.SealBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-batches")
@RequiredArgsConstructor
public class SealBatchController {

    private final SealBatchService sealBatchService;

    @GetMapping
    public ResponseEntity<List<SealBatchResponseDTO>> getAllSealBatches() {
        List<SealBatchResponseDTO> sealBatches = sealBatchService.findAll();
        return ResponseEntity.ok(sealBatches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealBatchResponseDTO> getSealBatchById(@PathVariable @NonNull UUID id) {
        UUID batchId = id;
        return sealBatchService.findById(batchId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealBatchResponseDTO> createSealBatch(@Valid @RequestBody @NonNull SealBatchRequestDTO sealBatchRequestDTO) {
        SealBatchResponseDTO createdBatch = sealBatchService.createSealBatch(sealBatchRequestDTO);
        return new ResponseEntity<>(createdBatch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SealBatchResponseDTO> updateSealBatch(@PathVariable @NonNull UUID id, @Valid @RequestBody @NonNull SealBatchRequestDTO sealBatchRequestDTO) {
        UUID batchId = id;
        SealBatchResponseDTO updatedBatch = sealBatchService.updateSealBatch(batchId, sealBatchRequestDTO);
        return ResponseEntity.ok(updatedBatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealBatch(@PathVariable @NonNull UUID id) {
        UUID batchId = id;
        sealBatchService.deleteById(batchId);
        return ResponseEntity.noContent().build();
    }
}
