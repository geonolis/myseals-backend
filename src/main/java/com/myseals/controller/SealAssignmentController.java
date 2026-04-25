package com.myseals.controller;

import com.myseals.dto.SealAssignmentRequestDTO;
import com.myseals.dto.SealAssignmentResponseDTO;
import com.myseals.service.SealAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-assignments")
@RequiredArgsConstructor
public class SealAssignmentController {

    private final SealAssignmentService sealAssignmentService;

    @GetMapping
    public ResponseEntity<List<SealAssignmentResponseDTO>> getAllSealAssignments() {
        return ResponseEntity.ok(sealAssignmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealAssignmentResponseDTO> getSealAssignmentById(@PathVariable @NonNull UUID id) {
        UUID assignmentId = id;
        return sealAssignmentService.findById(assignmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealAssignmentResponseDTO> createSealAssignment(@Valid @RequestBody @NonNull SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        SealAssignmentResponseDTO createdAssignment = sealAssignmentService.createSealAssignment(sealAssignmentRequestDTO);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SealAssignmentResponseDTO> updateSealAssignment(@PathVariable @NonNull UUID id, @Valid @RequestBody @NonNull SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        UUID assignmentId = id;
        SealAssignmentResponseDTO updatedAssignment = sealAssignmentService.updateSealAssignment(assignmentId, sealAssignmentRequestDTO);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealAssignment(@PathVariable @NonNull UUID id) {
        UUID assignmentId = id;
        sealAssignmentService.deleteById(assignmentId);
        return ResponseEntity.noContent().build();
    }
}
