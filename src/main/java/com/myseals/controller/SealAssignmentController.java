package com.myseals.controller;

import com.myseals.dto.SealAssignmentRequestDTO;
import com.myseals.dto.SealAssignmentResponseDTO;
import com.myseals.service.SealAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seal-assignments")
public class SealAssignmentController {

    @Autowired
    private SealAssignmentService sealAssignmentService;

    @GetMapping
    public ResponseEntity<List<SealAssignmentResponseDTO>> getAllSealAssignments() {
        List<SealAssignmentResponseDTO> sealAssignments = sealAssignmentService.findAll();
        return ResponseEntity.ok(sealAssignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealAssignmentResponseDTO> getSealAssignmentById(@PathVariable UUID id) {
        return sealAssignmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealAssignmentResponseDTO> createSealAssignment(@Valid @RequestBody SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        SealAssignmentResponseDTO createdSealAssignment = sealAssignmentService.createSealAssignment(sealAssignmentRequestDTO);
        return new ResponseEntity<>(createdSealAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SealAssignmentResponseDTO> updateSealAssignment(@PathVariable UUID id, @Valid @RequestBody SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        SealAssignmentResponseDTO updatedSealAssignment = sealAssignmentService.updateSealAssignment(id, sealAssignmentRequestDTO);
        return ResponseEntity.ok(updatedSealAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSealAssignment(@PathVariable UUID id) {
        sealAssignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
