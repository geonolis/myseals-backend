package com.myseals.controller;

import com.myseals.dto.OfficeRequestDTO;
import com.myseals.dto.OfficeResponseDTO;
import com.myseals.model.Office;
import com.myseals.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        List<Office> offices = officeService.findAll();
        List<OfficeResponseDTO> officeDTOs = offices.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(officeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable UUID id) {
        return officeService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@Valid @RequestBody OfficeRequestDTO officeRequestDTO) {
        Office office = new Office();
        office.setOfficeName(officeRequestDTO.getOfficeName());
        office.setOfficeCode(officeRequestDTO.getOfficeCode());
        office.setAddress(officeRequestDTO.getAddress());
        office.setContactEmail(officeRequestDTO.getContactEmail());
        office.setContactPhone(officeRequestDTO.getContactPhone());

        if (officeRequestDTO.getParentOfficeId() != null) {
            Office parentOffice = officeService.findById(officeRequestDTO.getParentOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent office not found"));
            office.setParentOffice(parentOffice);
        }

        Office savedOffice = officeService.save(office);
        return new ResponseEntity<>(convertToDto(savedOffice), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable UUID id, @Valid @RequestBody OfficeRequestDTO officeRequestDTO) {
        Office existingOffice = officeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        existingOffice.setOfficeName(officeRequestDTO.getOfficeName());
        existingOffice.setOfficeCode(officeRequestDTO.getOfficeCode());
        existingOffice.setAddress(officeRequestDTO.getAddress());
        existingOffice.setContactEmail(officeRequestDTO.getContactEmail());
        existingOffice.setContactPhone(officeRequestDTO.getContactPhone());

        if (officeRequestDTO.getParentOfficeId() != null) {
            Office parentOffice = officeService.findById(officeRequestDTO.getParentOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent office not found"));
            existingOffice.setParentOffice(parentOffice);
        } else {
            existingOffice.setParentOffice(null);
        }

        Office updatedOffice = officeService.save(existingOffice);
        return ResponseEntity.ok(convertToDto(updatedOffice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable UUID id) {
        officeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private OfficeResponseDTO convertToDto(Office office) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setOfficeId(office.getOfficeId());
        dto.setOfficeName(office.getOfficeName());
        dto.setOfficeCode(office.getOfficeCode());
        dto.setAddress(office.getAddress());
        dto.setContactEmail(office.getContactEmail());
        dto.setContactPhone(office.getContactPhone());
        dto.setCreatedAt(office.getCreatedAt());
        dto.setUpdatedAt(office.getUpdatedAt());
        if (office.getParentOffice() != null) {
            dto.setParentOfficeId(office.getParentOffice().getOfficeId());
            dto.setParentOfficeName(office.getParentOffice().getOfficeName());
        }
        return dto;
    }
}
