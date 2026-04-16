package com.myseals.service;

import com.myseals.dto.SealRequestDTO;
import com.myseals.dto.SealResponseDTO;
import com.myseals.model.*;
import com.myseals.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SealService {

    @Autowired
    private SealRepository sealRepository;
    @Autowired
    private SealBatchRepository sealBatchRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private UserRepository userRepository;

    public List<SealResponseDTO> findAll() {
        return sealRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealResponseDTO> findById(UUID id) {
        return sealRepository.findById(id).map(this::convertToDto);
    }

    public SealResponseDTO createSeal(SealRequestDTO sealRequestDTO) {
        if (sealRepository.findBySealNumber(sealRequestDTO.getSealNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seal with this number already exists");
        }

        SealBatch batch = sealBatchRepository.findById(sealRequestDTO.getBatchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));

        Office currentOffice = officeRepository.findById(sealRequestDTO.getCurrentOfficeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        User assignedToUser = null;
        if (sealRequestDTO.getAssignedToUserId() != null) {
            assignedToUser = userRepository.findById(sealRequestDTO.getAssignedToUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
        }

        Seal seal = new Seal();
        seal.setSealNumber(sealRequestDTO.getSealNumber());
        seal.setBatch(batch);
        seal.setCurrentStatus(SealStatus.valueOf(sealRequestDTO.getCurrentStatus()));
        seal.setCurrentOffice(currentOffice);
        seal.setAssignedToUser(assignedToUser);

        Seal savedSeal = sealRepository.save(seal);
        return convertToDto(savedSeal);
    }

    public SealResponseDTO updateSeal(UUID id, SealRequestDTO sealRequestDTO) {
        Seal existingSeal = sealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        existingSeal.setSealNumber(sealRequestDTO.getSealNumber());

        if (!existingSeal.getBatch().getBatchId().equals(sealRequestDTO.getBatchId())) {
            SealBatch batch = sealBatchRepository.findById(sealRequestDTO.getBatchId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal batch not found"));
            existingSeal.setBatch(batch);
        }

        existingSeal.setCurrentStatus(SealStatus.valueOf(sealRequestDTO.getCurrentStatus()));

        if (!existingSeal.getCurrentOffice().getOfficeId().equals(sealRequestDTO.getCurrentOfficeId())) {
            Office currentOffice = officeRepository.findById(sealRequestDTO.getCurrentOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            existingSeal.setCurrentOffice(currentOffice);
        }

        if (sealRequestDTO.getAssignedToUserId() != null) {
            if (existingSeal.getAssignedToUser() == null || !existingSeal.getAssignedToUser().getUserId().equals(sealRequestDTO.getAssignedToUserId())) {
                User assignedToUser = userRepository.findById(sealRequestDTO.getAssignedToUserId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
                existingSeal.setAssignedToUser(assignedToUser);
            }
        } else {
            existingSeal.setAssignedToUser(null);
        }

        Seal updatedSeal = sealRepository.save(existingSeal);
        return convertToDto(updatedSeal);
    }

    public void deleteById(UUID id) {
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
