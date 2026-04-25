package com.myseals.service;

import com.myseals.dto.StockMovementRequestDTO;
import com.myseals.dto.StockMovementResponseDTO;
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
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final SealRepository sealRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public List<StockMovementResponseDTO> findAll() {
        return stockMovementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<StockMovementResponseDTO> findById(@NonNull UUID id) {
        return stockMovementRepository.findById(id).map(this::convertToDto);
    }

    public StockMovementResponseDTO createStockMovement(@NonNull StockMovementRequestDTO stockMovementRequestDTO) {
        UUID sealId = stockMovementRequestDTO.getSealId();
        Seal seal = sealRepository.findById(sealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        StockMovement movement = new StockMovement();
        movement.setSeal(seal);
        movement.setMovementType(MovementType.valueOf(stockMovementRequestDTO.getMovementType()));
        movement.setNotes(stockMovementRequestDTO.getNotes());

        UUID fromOfficeId = stockMovementRequestDTO.getFromOfficeId();
        if (fromOfficeId != null) {
            Office fromOffice = officeRepository.findById(fromOfficeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Source office not found"));
            movement.setFromOffice(fromOffice);
        }

        UUID toOfficeId = stockMovementRequestDTO.getToOfficeId();
        if (toOfficeId != null) {
            Office toOffice = officeRepository.findById(toOfficeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination office not found"));
            movement.setToOffice(toOffice);
        }

        UUID fromUserId = stockMovementRequestDTO.getFromUserId();
        if (fromUserId != null) {
            User fromUser = userRepository.findById(fromUserId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Source user not found"));
            movement.setFromUser(fromUser);
        }

        UUID toUserId = stockMovementRequestDTO.getToUserId();
        if (toUserId != null) {
            User toUser = userRepository.findById(toUserId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination user not found"));
            movement.setToUser(toUser);
        }

        UUID movedByUserId = stockMovementRequestDTO.getMovedByUserId();
        User movedBy = userRepository.findById(movedByUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mover user not found"));
        movement.setMovedBy(movedBy);

        StockMovement savedMovement = stockMovementRepository.save(movement);
        return convertToDto(savedMovement);
    }

    public void deleteById(@NonNull UUID id) {
        stockMovementRepository.deleteById(id);
    }

    private StockMovementResponseDTO convertToDto(StockMovement movement) {
        StockMovementResponseDTO dto = new StockMovementResponseDTO();
        dto.setMovementId(movement.getMovementId());
        dto.setSealId(movement.getSeal().getSealId());
        dto.setMovementType(movement.getMovementType().name());
        dto.setMovementDate(movement.getMovementDate());
        dto.setNotes(movement.getNotes());
        dto.setCreatedAt(movement.getCreatedAt());

        if (movement.getFromOffice() != null) {
            dto.setFromOfficeId(movement.getFromOffice().getOfficeId());
        }
        if (movement.getToOffice() != null) {
            dto.setToOfficeId(movement.getToOffice().getOfficeId());
        }
        if (movement.getFromUser() != null) {
            dto.setFromUserId(movement.getFromUser().getUserId());
        }
        if (movement.getToUser() != null) {
            dto.setToUserId(movement.getToUser().getUserId());
        }
        if (movement.getMovedBy() != null) {
            dto.setMovedByUserId(movement.getMovedBy().getUserId());
        }

        return dto;
    }
}
