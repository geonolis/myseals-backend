package com.myseals.service;

import com.myseals.dto.SealRequestDTO;
import com.myseals.dto.SealResponseDTO;
import com.myseals.model.*;
import com.myseals.repository.*;
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
public class SealService {

    private final SealRepository sealRepository;
    private final SealBatchRepository sealBatchRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public List<SealResponseDTO> findAll() {
        return sealRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealResponseDTO> findById(@NonNull UUID id) {
        return sealRepository.findById(id).map(this::convertToDto);
    }

    public SealResponseDTO createSeal(@NonNull SealRequestDTO sealRequestDTO) {
        if (sealRepository.findBySealNumber(sealRequestDTO.getSealNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seal number already exists");
        }

        Seal seal = new Seal();
        seal.setSealNumber(sealRequestDTO.getSealNumber());
        
        if (sealRequestDTO.getCurrentStatus() != null) {
            seal.setCurrentStatus(SealStatus.valueOf(sealRequestDTO.getCurrentStatus()));
        } else {
            seal.setCurrentStatus(SealStatus.REGISTERED); // Default status
        }

        UUID batchId = sealRequestDTO.getBatchId();
        if (batchId != null) {
            SealBatch batch = sealBatchRepository.findById(batchId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));
            seal.setBatch(batch);
        }

        UUID officeId = sealRequestDTO.getCurrentOfficeId();
        if (officeId != null) {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            seal.setCurrentOffice(office);
        }

        UUID userId = sealRequestDTO.getAssignedToUserId();
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            seal.setAssignedToUser(user);
        }

        Seal savedSeal = sealRepository.save(seal);
        return convertToDto(savedSeal);
    }

    public SealResponseDTO updateSeal(@NonNull UUID id, @NonNull SealRequestDTO sealRequestDTO) {
        Seal existingSeal = sealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        existingSeal.setSealNumber(sealRequestDTO.getSealNumber());
        
        if (sealRequestDTO.getCurrentStatus() != null) {
            existingSeal.setCurrentStatus(SealStatus.valueOf(sealRequestDTO.getCurrentStatus()));
        }

        UUID batchId = sealRequestDTO.getBatchId();
        if (batchId != null) {
            SealBatch batch = sealBatchRepository.findById(batchId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));
            existingSeal.setBatch(batch);
        }

        UUID officeId = sealRequestDTO.getCurrentOfficeId();
        if (officeId != null) {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            existingSeal.setCurrentOffice(office);
        }

        UUID userId = sealRequestDTO.getAssignedToUserId();
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            existingSeal.setAssignedToUser(user);
        } else {
            existingSeal.setAssignedToUser(null);
        }

        Seal updatedSeal = sealRepository.save(existingSeal);
        return convertToDto(updatedSeal);
    }

    public void deleteById(@NonNull UUID id) {
        sealRepository.deleteById(id);
    }

    private SealResponseDTO convertToDto(Seal seal) {
        SealResponseDTO dto = new SealResponseDTO();
        dto.setSealId(seal.getSealId());
        dto.setSealNumber(seal.getSealNumber());
        dto.setCurrentStatus(seal.getCurrentStatus().name());
        dto.setRegisteredAt(seal.getRegisteredAt());
        dto.setLastUpdatedAt(seal.getLastUpdatedAt());

        if (seal.getBatch() != null) {
            dto.setBatchId(seal.getBatch().getBatchId());
            dto.setBatchNumber(seal.getBatch().getBatchNumber());
            dto.setManufacturer(seal.getBatch().getManufacturer());
        }
        if (seal.getCurrentOffice() != null) {
            dto.setCurrentOfficeId(seal.getCurrentOffice().getOfficeId());
            dto.setCurrentOfficeName(seal.getCurrentOffice().getOfficeName());
        }
        if (seal.getAssignedToUser() != null) {
            dto.setAssignedToUserId(seal.getAssignedToUser().getUserId());
            dto.setAssignedToUserName(seal.getAssignedToUser().getFullName());
        }
        return dto;
    }
}
