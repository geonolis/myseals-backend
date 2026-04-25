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
public class SealAssignmentService {

    private final SealAssignmentRepository sealAssignmentRepository;
    private final SealRepository sealRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    public List<SealAssignmentResponseDTO> findAll() {
        return sealAssignmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<SealAssignmentResponseDTO> findById(@NonNull UUID id) {
        return sealAssignmentRepository.findById(id).map(this::convertToDto);
    }

    public SealAssignmentResponseDTO createSealAssignment(@NonNull SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        UUID sealId = sealAssignmentRequestDTO.getSealId();
        Seal seal = sealRepository.findById(sealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        if (sealAssignmentRepository.findBySealSealId(seal.getSealId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seal is already assigned");
        }

        User assignedToUser = null;
        UUID assignedToUserId = sealAssignmentRequestDTO.getAssignedToUserId();
        if (assignedToUserId != null) {
            assignedToUser = userRepository.findById(assignedToUserId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
        }

        Office assignedToOffice = null;
        UUID assignedToOfficeId = sealAssignmentRequestDTO.getAssignedToOfficeId();
        if (assignedToOfficeId != null) {
            assignedToOffice = officeRepository.findById(assignedToOfficeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned office not found"));
        }

        UUID assignedByUserId = sealAssignmentRequestDTO.getAssignedByUserId();
        User assignedBy = userRepository.findById(assignedByUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigning user not found"));

        SealAssignment assignment = new SealAssignment();
        assignment.setSeal(seal);
        assignment.setAssignedToUser(assignedToUser);
        assignment.setAssignedToOffice(assignedToOffice);
        assignment.setAssignedBy(assignedBy);
        assignment.setAssignmentDate(OffsetDateTime.now());
        assignment.setStatus(sealAssignmentRequestDTO.getStatus());

        SealAssignment savedAssignment = sealAssignmentRepository.save(assignment);
        return convertToDto(savedAssignment);
    }

    public SealAssignmentResponseDTO updateSealAssignment(@NonNull UUID id, @NonNull SealAssignmentRequestDTO sealAssignmentRequestDTO) {
        SealAssignment existingAssignment = sealAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal assignment not found"));

        User assignedToUser = null;
        UUID assignedToUserId = sealAssignmentRequestDTO.getAssignedToUserId();
        if (assignedToUserId != null) {
            assignedToUser = userRepository.findById(assignedToUserId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
        }

        Office assignedToOffice = null;
        UUID assignedToOfficeId = sealAssignmentRequestDTO.getAssignedToOfficeId();
        if (assignedToOfficeId != null) {
            assignedToOffice = officeRepository.findById(assignedToOfficeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned office not found"));
        }

        UUID assignedByUserId = sealAssignmentRequestDTO.getAssignedByUserId();
        User assignedBy = userRepository.findById(assignedByUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigning user not found"));

        existingAssignment.setAssignedToUser(assignedToUser);
        existingAssignment.setAssignedToOffice(assignedToOffice);
        existingAssignment.setAssignedBy(assignedBy);
        existingAssignment.setStatus(sealAssignmentRequestDTO.getStatus());

        SealAssignment updatedAssignment = sealAssignmentRepository.save(existingAssignment);
        return convertToDto(updatedAssignment);
    }

    public void deleteById(@NonNull UUID id) {
        sealAssignmentRepository.deleteById(id);
    }

    private SealAssignmentResponseDTO convertToDto(SealAssignment assignment) {
        SealAssignmentResponseDTO dto = new SealAssignmentResponseDTO();
        dto.setAssignmentId(assignment.getAssignmentId());
        dto.setSealId(assignment.getSeal().getSealId());
        dto.setSealNumber(assignment.getSeal().getSealNumber());
        dto.setAssignmentDate(assignment.getAssignmentDate());
        dto.setStatus(assignment.getStatus());

        if (assignment.getAssignedToUser() != null) {
            dto.setAssignedToUserId(assignment.getAssignedToUser().getUserId());
            dto.setAssignedToUserName(assignment.getAssignedToUser().getFullName());
        }

        if (assignment.getAssignedToOffice() != null) {
            dto.setAssignedToOfficeId(assignment.getAssignedToOffice().getOfficeId());
            dto.setAssignedToOfficeName(assignment.getAssignedToOffice().getOfficeName());
        }

        if (assignment.getAssignedBy() != null) {
            dto.setAssignedByUserId(assignment.getAssignedBy().getUserId());
            dto.setAssignedByUserName(assignment.getAssignedBy().getFullName());
        }

        return dto;
    }
}
