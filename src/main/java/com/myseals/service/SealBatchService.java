package com.myseals.service;

import com.myseals.dto.SealBatchRequestDTO;
import com.myseals.dto.SealBatchResponseDTO;
import com.myseals.model.SealBatch;
import com.myseals.model.User;
import com.myseals.repository.SealBatchRepository;
import com.myseals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SealBatchService {

    @Autowired
    private SealBatchRepository sealBatchRepository;
    @Autowired
    private UserRepository userRepository;

    public List<SealBatchResponseDTO> findAll() {
        return sealBatchRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealBatchResponseDTO> findById(UUID id) {
        return sealBatchRepository.findById(id).map(this::convertToDto);
    }

    public SealBatchResponseDTO createSealBatch(SealBatchRequestDTO sealBatchRequestDTO) {
        if (sealBatchRepository.findByBatchNumber(sealBatchRequestDTO.getBatchNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seal batch with this number already exists");
        }

        User registeredBy = null;
        if (sealBatchRequestDTO.getRegisteredByUserId() != null) {
            registeredBy = userRepository.findById(sealBatchRequestDTO.getRegisteredByUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registered by user not found"));
        }

        SealBatch sealBatch = new SealBatch();
        sealBatch.setManufacturer(sealBatchRequestDTO.getManufacturer());
        sealBatch.setBatchNumber(sealBatchRequestDTO.getBatchNumber());
        sealBatch.setStartSealNumber(sealBatchRequestDTO.getStartSealNumber());
        sealBatch.setEndSealNumber(sealBatchRequestDTO.getEndSealNumber());
        sealBatch.setQuantity(sealBatchRequestDTO.getQuantity());
        sealBatch.setReceivedDate(sealBatchRequestDTO.getReceivedDate());
        sealBatch.setRegisteredBy(registeredBy);

        SealBatch savedSealBatch = sealBatchRepository.save(sealBatch);
        return convertToDto(savedSealBatch);
    }

    public SealBatchResponseDTO updateSealBatch(UUID id, SealBatchRequestDTO sealBatchRequestDTO) {
        SealBatch existingSealBatch = sealBatchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));

        existingSealBatch.setManufacturer(sealBatchRequestDTO.getManufacturer());
        existingSealBatch.setBatchNumber(sealBatchRequestDTO.getBatchNumber());
        existingSealBatch.setStartSealNumber(sealBatchRequestDTO.getStartSealNumber());
        existingSealBatch.setEndSealNumber(sealBatchRequestDTO.getEndSealNumber());
        existingSealBatch.setQuantity(sealBatchRequestDTO.getQuantity());
        existingSealBatch.setReceivedDate(sealBatchRequestDTO.getReceivedDate());

        if (sealBatchRequestDTO.getRegisteredByUserId() != null) {
            User registeredBy = userRepository.findById(sealBatchRequestDTO.getRegisteredByUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registered by user not found"));
            existingSealBatch.setRegisteredBy(registeredBy);
        } else {
            existingSealBatch.setRegisteredBy(null);
        }

        SealBatch updatedSealBatch = sealBatchRepository.save(existingSealBatch);
        return convertToDto(updatedSealBatch);
    }

    public void deleteById(UUID id) {
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
