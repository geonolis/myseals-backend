package com.myseals.controller;

import com.myseals.dto.SealRequestDTO;
import com.myseals.dto.SealResponseDTO;
import com.myseals.service.SealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seals")
public class SealController {

    @Autowired
    private SealService sealService;

    @GetMapping
    public ResponseEntity<List<SealResponseDTO>> getAllSeals() {
        List<SealResponseDTO> seals = sealService.findAll();
        return ResponseEntity.ok(seals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SealResponseDTO> getSealById(@PathVariable UUID id) {
        return sealService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SealResponseDTO> createSeal(@Valid @RequestBody SealRequestDTO sealRequestDTO) {
        SealResponseDTO createdSeal = sealService.createSeal(sealRequestDTO);
        return new ResponseEntity<>(createdSeal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SealResponseDTO> updateSeal(@PathVariable UUID id, @Valid @RequestBody SealRequestDTO sealRequestDTO) {
        SealResponseDTO updatedSeal = sealService.updateSeal(id, sealRequestDTO);
        return ResponseEntity.ok(updatedSeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeal(@PathVariable UUID id) {
        sealService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
