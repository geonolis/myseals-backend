package com.myseals.controller;

import com.myseals.dto.OfficeRequestDTO;
import com.myseals.dto.OfficeResponseDTO;
import com.myseals.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable @NonNull UUID id) {
        UUID officeId = id;
        return officeService.findById(officeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@Valid @RequestBody @NonNull OfficeRequestDTO officeRequestDTO) {
        OfficeResponseDTO createdOffice = officeService.createOffice(officeRequestDTO);
        return new ResponseEntity<>(createdOffice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable @NonNull UUID id, @Valid @RequestBody @NonNull OfficeRequestDTO officeRequestDTO) {
        UUID officeId = id;
        OfficeResponseDTO updatedOffice = officeService.updateOffice(officeId, officeRequestDTO);
        return ResponseEntity.ok(updatedOffice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable @NonNull UUID id) {
        UUID officeId = id;
        officeService.deleteById(officeId);
        return ResponseEntity.noContent().build();
    }
}
