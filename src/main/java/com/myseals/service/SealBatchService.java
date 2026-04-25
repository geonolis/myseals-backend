package com.myseals.service;

import com.myseals.dto.SealBatchRequestDTO;
import com.myseals.dto.SealBatchResponseDTO;
import com.myseals.model.SealBatch;
import com.myseals.model.User;
import com.myseals.repository.SealBatchRepository;
import com.myseals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SealBatchService {

    private final SealBatchRepository sealBatchRepository;
    private final UserRepository userRepository;

    public List<SealBatchResponseDTO> findAll() {
        return sealBatchRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealBatchResponseDTO> findById(@NonNull UUID id) {
        return sealBatchRepository.findById(id).map(this::convertToDto);
    }

    public SealBatchResponseDTO createSealBatch(@NonNull SealBatchRequestDTO sealBatchRequestDTO) {
        if (sealBatchRepository.findByBatchNumber(sealBatchRequestDTO.getBatchNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Batch number already exists");
        }

        SealBatch sealBatch = new SealBatch();
        sealBatch.setManufacturer(sealBatchRequestDTO.getManufacturer());
        sealBatch.setBatchNumber(sealBatchRequestDTO.getBatchNumber());
        sealBatch.setStartSealNumber(sealBatchRequestDTO.getStartSealNumber());
        sealBatch.setEndSealNumber(sealBatchRequestDTO.getEndSealNumber());
        sealBatch.setQuantity(sealBatchRequestDTO.getQuantity());
        sealBatch.setReceivedDate(sealBatchRequestDTO.getReceivedDate());

        UUID userId = sealBatchRequestDTO.getRegisteredByUserId();
        if (userId != null) {
            User registeredBy = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            sealBatch.setRegisteredBy(registeredBy);
        }

        SealBatch savedBatch = sealBatchRepository.save(sealBatch);
        return convertToDto(savedBatch);
    }

    public SealBatchResponseDTO updateSealBatch(@NonNull UUID id, @NonNull SealBatchRequestDTO sealBatchRequestDTO) {
        SealBatch existingBatch = sealBatchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));

        existingBatch.setManufacturer(sealBatchRequestDTO.getManufacturer());
        existingBatch.setBatchNumber(sealBatchRequestDTO.getBatchNumber());
        existingBatch.setStartSealNumber(sealBatchRequestDTO.getStartSealNumber());
        existingBatch.setEndSealNumber(sealBatchRequestDTO.getEndSealNumber());
        existingBatch.setQuantity(sealBatchRequestDTO.getQuantity());
        existingBatch.setReceivedDate(sealBatchRequestDTO.getReceivedDate());

        UUID userId = sealBatchRequestDTO.getRegisteredByUserId();
        if (userId != null) {
            User registeredBy = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            existingBatch.setRegisteredBy(registeredBy);
        }

        SealBatch updatedBatch = sealBatchRepository.save(existingBatch);
        return convertToDto(updatedBatch);
    }

    public void deleteById(@NonNull UUID id) {
        sealBatchRepository.deleteById(id);
    }

    private SealBatchResponseDTO convertToDto(SealBatch sealBatch) {
        SealBatchResponseDTO dto = new SealBatchResponseDTO();
        dto.setBatchId(sealBatch.getBatchId());
        dto.setManufacturer(sealBatch.getManufacturer());
        dto.setBatchNumber(sealBatch.getBatchNumber());
        dto.setStartSealNumber(sealBatch.getStartSealNumber());
        dto.setEndSealNumber(sealBatch.getEndSealNumber());
        dto.setQuantity(sealBatch.getQuantity());
        dto.setReceivedDate(sealBatch.getReceivedDate());
        dto.setCreatedAt(sealBatch.getCreatedAt());
        dto.setUpdatedAt(sealBatch.getUpdatedAt());

        if (sealBatch.getRegisteredBy() != null) {
            dto.setRegisteredByUserId(sealBatch.getRegisteredBy().getUserId());
            dto.setRegisteredByUserName(sealBatch.getRegisteredBy().getFullName());
        }
        return dto;
    }
}
