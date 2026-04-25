package com.myseals.service;

import com.myseals.dto.SealUsageRequestDTO;
import com.myseals.dto.SealUsageResponseDTO;
import com.myseals.model.Seal;
import com.myseals.model.SealUsage;
import com.myseals.model.User;
import com.myseals.repository.SealRepository;
import com.myseals.repository.SealUsageRepository;
import com.myseals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SealUsageService {

    private final SealUsageRepository sealUsageRepository;
    private final SealRepository sealRepository;
    private final UserRepository userRepository;

    public List<SealUsageResponseDTO> findAll() {
        return sealUsageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealUsageResponseDTO> findById(@NonNull UUID id) {
        return sealUsageRepository.findById(id).map(this::convertToDto);
    }

    public SealUsageResponseDTO createSealUsage(@NonNull SealUsageRequestDTO sealUsageRequestDTO) {
        UUID sealId = sealUsageRequestDTO.getSealId();
        Seal seal = sealRepository.findById(sealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        UUID userId = sealUsageRequestDTO.getUsedByUserId();
        User usedBy = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        SealUsage usage = new SealUsage();
        usage.setSeal(seal);
        usage.setUsedBy(usedBy);
        usage.setUsageLocation(sealUsageRequestDTO.getUsageLocation());
        usage.setLatitude(sealUsageRequestDTO.getLatitude());
        usage.setLongitude(sealUsageRequestDTO.getLongitude());
        usage.setDocumentReference(sealUsageRequestDTO.getDocumentReference());
        usage.setPhotoUrl(sealUsageRequestDTO.getPhotoUrl());
        usage.setNotes(sealUsageRequestDTO.getNotes());
        usage.setUsedAt(OffsetDateTime.now());

        SealUsage savedUsage = sealUsageRepository.save(usage);
        return convertToDto(savedUsage);
    }

    public void deleteById(@NonNull UUID id) {
        sealUsageRepository.deleteById(id);
    }

    private SealUsageResponseDTO convertToDto(SealUsage usage) {
        SealUsageResponseDTO dto = new SealUsageResponseDTO();
        dto.setUsageId(usage.getUsageId());
        dto.setSealId(usage.getSeal().getSealId());
        dto.setSealNumber(usage.getSeal().getSealNumber());
        dto.setUsageLocation(usage.getUsageLocation());
        dto.setLatitude(usage.getLatitude());
        dto.setLongitude(usage.getLongitude());
        dto.setDocumentReference(usage.getDocumentReference());
        dto.setPhotoUrl(usage.getPhotoUrl());
        dto.setNotes(usage.getNotes());
        dto.setUsedAt(usage.getUsedAt());
        dto.setCreatedAt(usage.getCreatedAt());

        if (usage.getUsedBy() != null) {
            dto.setUsedByUserId(usage.getUsedBy().getUserId());
            dto.setUsedByUserName(usage.getUsedBy().getFullName());
        }

        return dto;
    }
}
