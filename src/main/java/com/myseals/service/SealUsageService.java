package com.myseals.service;

import com.myseals.dto.SealUsageRequestDTO;
import com.myseals.dto.SealUsageResponseDTO;
import com.myseals.model.Seal;
import com.myseals.model.SealUsage;
import com.myseals.model.User;
import com.myseals.repository.SealRepository;
import com.myseals.repository.SealUsageRepository;
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
public class SealUsageService {

    @Autowired
    private SealUsageRepository sealUsageRepository;
    @Autowired
    private SealRepository sealRepository;
    @Autowired
    private UserRepository userRepository;

    public List<SealUsageResponseDTO> findAll() {
        return sealUsageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealUsageResponseDTO> findById(UUID id) {
        return sealUsageRepository.findById(id).map(this::convertToDto);
    }

    public SealUsageResponseDTO createSealUsage(SealUsageRequestDTO sealUsageRequestDTO) {
        Seal seal = sealRepository.findById(sealUsageRequestDTO.getSealId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        User usedBy = userRepository.findById(sealUsageRequestDTO.getUsedByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        SealUsage sealUsage = new SealUsage();
        sealUsage.setSeal(seal);
        sealUsage.setUsedBy(usedBy);
        sealUsage.setUsageLocation(sealUsageRequestDTO.getUsageLocation());
        sealUsage.setLatitude(sealUsageRequestDTO.getLatitude());
        sealUsage.setLongitude(sealUsageRequestDTO.getLongitude());
        sealUsage.setDocumentReference(sealUsageRequestDTO.getDocumentReference());
        sealUsage.setPhotoUrl(sealUsageRequestDTO.getPhotoUrl());
        sealUsage.setNotes(sealUsageRequestDTO.getNotes());

        SealUsage savedSealUsage = sealUsageRepository.save(sealUsage);
        return convertToDto(savedSealUsage);
    }

    public void deleteById(UUID id) {
        sealUsageRepository.deleteById(id);
    }

    private SealUsageResponseDTO convertToDto(SealUsage sealUsage) {
        SealUsageResponseDTO dto = new SealUsageResponseDTO();
        dto.setUsageId(sealUsage.getUsageId());
        dto.setSealId(sealUsage.getSeal().getSealId());
        dto.setSealNumber(sealUsage.getSeal().getSealNumber());
        dto.setUsedByUserId(sealUsage.getUsedBy().getUserId());
        dto.setUsedByUserName(sealUsage.getUsedBy().getFullName());
        dto.setUsageLocation(sealUsage.getUsageLocation());
        dto.setLatitude(sealUsage.getLatitude());
        dto.setLongitude(sealUsage.getLongitude());
        dto.setDocumentReference(sealUsage.getDocumentReference());
        dto.setPhotoUrl(sealUsage.getPhotoUrl());
        dto.setNotes(sealUsage.getNotes());
        dto.setUsedAt(sealUsage.getUsedAt());
        dto.setCreatedAt(sealUsage.getCreatedAt());
        return dto;
    }
}
