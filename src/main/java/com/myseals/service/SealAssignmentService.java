package com.myseals.service;

import com.myseals.dto.SealAssignmentRequestDTO;
import com.myseals.dto.SealAssignmentResponseDTO;
import com.myseals.model.Office;
import com.myseals.model.Seal;
import com.myseals.model.SealAssignment;
import com.myseals.model.User;
import com.myseals.repository.OfficeRepository;
import com.myseals.repository.SealAssignmentRepository;
import com.myseals.repository.SealRepository;
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
public class SealAssignmentService {

    @Autowired
    private SealAssignmentRepository sealAssignmentRepository;
    @Autowired
    private SealRepository sealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfficeRepository officeRepository;

    public List<SealAssignmentResponseDTO> findAll() {
        return sealAssignmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealAssignmentResponseDTO> findById(UUID id) {
        return sealAssignmentRepository.findById(id).map(this::convertToDto);
    }

    public SealAssignmentResponseDTO createSealAssignment(SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        Seal seal = sealRepository.findById(sealAssignmentRequestDTO.getSealId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        if (sealAssignmentRepository.findBySealSealId(seal.getSealId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seal is already assigned");
        }

        User assignedToUser = null;
        if (sealAssignmentRequestDTO.getAssignedToUserId() != null) {
            assignedToUser = userRepository.findById(sealAssignmentRequestDTO.getAssignedToUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
        }

        Office assignedToOffice = null;
        if (sealAssignmentRequestDTO.getAssignedToOfficeId() != null) {
            assignedToOffice = officeRepository.findById(sealAssignmentRequestDTO.getAssignedToOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned office not found"));
        }

        User assignedBy = userRepository.findById(sealAssignmentRequestDTO.getAssignedByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigning user not found"));

        SealAssignment sealAssignment = new SealAssignment();
        sealAssignment.setSeal(seal);
        sealAssignment.setAssignedToUser(assignedToUser);
        sealAssignment.setAssignedToOffice(assignedToOffice);
        sealAssignment.setAssignedBy(assignedBy);
        sealAssignment.setStatus(sealAssignmentRequestDTO.getStatus());

        SealAssignment savedSealAssignment = sealAssignmentRepository.save(sealAssignment);
        return convertToDto(savedSealAssignment);
    }

    public SealAssignmentResponseDTO updateSealAssignment(UUID id, SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        SealAssignment existingAssignment = sealAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal assignment not found"));

        // Seal cannot be changed after assignment

        User assignedToUser = null;
        if (sealAssignmentRequestDTO.getAssignedToUserId() != null) {
            assignedToUser = userRepository.findById(sealAssignmentRequestDTO.getAssignedToUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
        }

        Office assignedToOffice = null;
        if (sealAssignmentRequestDTO.getAssignedToOfficeId() != null) {
            assignedToOffice = officeRepository.findById(sealAssignmentRequestDTO.getAssignedToOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned office not found"));
        }

        User assignedBy = userRepository.findById(sealAssignmentRequestDTO.getAssignedByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigning user not found"));

        existingAssignment.setAssignedToUser(assignedToUser);
        existingAssignment.setAssignedToOffice(assignedToOffice);
        existingAssignment.setAssignedBy(assignedBy);
        existingAssignment.setReturnDate(sealAssignmentRequestDTO.getReturnDate());
        existingAssignment.setStatus(sealAssignmentRequestDTO.getStatus());

        SealAssignment updatedAssignment = sealAssignmentRepository.save(existingAssignment);
        return convertToDto(updatedAssignment);
    }

    public void deleteById(UUID id) {
        sealAssignmentRepository.deleteById(id);
    }

    private SealAssignmentResponseDTO convertToDto(SealAssignment sealAssignment) {
        SealAssignmentResponseDTO dto = new SealAssignmentResponseDTO();
        dto.setAssignmentId(sealAssignment.getAssignmentId());
        dto.setSealId(sealAssignment.getSeal().getSealId());
        dto.setSealNumber(sealAssignment.getSeal().getSealNumber());
        dto.setAssignmentDate(sealAssignment.getAssignmentDate());
        dto.setReturnDate(sealAssignment.getReturnDate());
        dto.setStatus(sealAssignment.getStatus());
        dto.setCreatedAt(sealAssignment.getCreatedAt());
        dto.setUpdatedAt(sealAssignment.getUpdatedAt());

        if (sealAssignment.getAssignedToUser() != null) {
            dto.setAssignedToUserId(sealAssignment.getAssignedToUser().getUserId());
            dto.setAssignedToUserName(sealAssignment.getAssignedToUser().getFullName());
        }
        if (sealAssignment.getAssignedToOffice() != null) {
            dto.setAssignedToOfficeId(sealAssignment.getAssignedToOffice().getOfficeId());
            dto.setAssignedToOfficeName(sealAssignment.getAssignedToOffice().getOfficeName());
        }
        if (sealAssignment.getAssignedBy() != null) {
            dto.setAssignedByUserId(sealAssignment.getAssignedBy().getUserId());
            dto.setAssignedByUserName(sealAssignment.getAssignedBy().getFullName());
        }
        return dto;
    }
}
