package com.myseals.service;

import com.myseals.dto.StockMovementRequestDTO;
import com.myseals.dto.StockMovementResponseDTO;
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
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private SealRepository sealRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private UserRepository userRepository;

    public List<StockMovementResponseDTO> findAll() {
        return stockMovementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<StockMovementResponseDTO> findById(UUID id) {
        return stockMovementRepository.findById(id).map(this::convertToDto);
    }

    public StockMovementResponseDTO createStockMovement(StockMovementRequestDTO stockMovementRequestDTO) {
        Seal seal = sealRepository.findById(stockMovementRequestDTO.getSealId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seal not found"));

        Office fromOffice = null;
        if (stockMovementRequestDTO.getFromOfficeId() != null) {
            fromOffice = officeRepository.findById(stockMovementRequestDTO.getFromOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "From Office not found"));
        }

        Office toOffice = null;
        if (stockMovementRequestDTO.getToOfficeId() != null) {
            toOffice = officeRepository.findById(stockMovementRequestDTO.getToOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To Office not found"));
        }

        User fromUser = null;
        if (stockMovementRequestDTO.getFromUserId() != null) {
            fromUser = userRepository.findById(stockMovementRequestDTO.getFromUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "From User not found"));
        }

        User toUser = null;
        if (stockMovementRequestDTO.getToUserId() != null) {
            toUser = userRepository.findById(stockMovementRequestDTO.getToUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To User not found"));
        }

        User movedBy = userRepository.findById(stockMovementRequestDTO.getMovedByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moved By User not found"));

        StockMovement stockMovement = new StockMovement();
        stockMovement.setSeal(seal);
        stockMovement.setMovementType(MovementType.valueOf(stockMovementRequestDTO.getMovementType()));
        stockMovement.setFromOffice(fromOffice);
        stockMovement.setToOffice(toOffice);
        stockMovement.setFromUser(fromUser);
        stockMovement.setToUser(toUser);
        stockMovement.setMovedBy(movedBy);
        stockMovement.setNotes(stockMovementRequestDTO.getNotes());

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);
        return convertToDto(savedStockMovement);
    }

    public void deleteById(UUID id) {
        stockMovementRepository.deleteById(id);
    }

    private StockMovementResponseDTO convertToDto(StockMovement stockMovement) {
        StockMovementResponseDTO dto = new StockMovementResponseDTO();
        dto.setMovementId(stockMovement.getMovementId());
        dto.setSealId(stockMovement.getSeal().getSealId());
        dto.setSealNumber(stockMovement.getSeal().getSealNumber());
        dto.setMovementType(stockMovement.getMovementType().name());
        dto.setNotes(stockMovement.getNotes());
        dto.setMovementDate(stockMovement.getMovementDate());
        dto.setCreatedAt(stockMovement.getCreatedAt());

        if (stockMovement.getFromOffice() != null) {
            dto.setFromOfficeId(stockMovement.getFromOffice().getOfficeId());
            dto.setFromOfficeName(stockMovement.getFromOffice().getOfficeName());
        }
        if (stockMovement.getToOffice() != null) {
            dto.setToOfficeId(stockMovement.getToOffice().getOfficeId());
            dto.setToOfficeName(stockMovement.getToOffice().getOfficeName());
        }
        if (stockMovement.getFromUser() != null) {
            dto.setFromUserId(stockMovement.getFromUser().getUserId());
            dto.setFromUserName(stockMovement.getFromUser().getFullName());
        }
        if (stockMovement.getToUser() != null) {
            dto.setToUserId(stockMovement.getToUser().getUserId());
            dto.setToUserName(stockMovement.getToUser().getFullName());
        }
        if (stockMovement.getMovedBy() != null) {
            dto.setMovedByUserId(stockMovement.getMovedBy().getUserId());
            dto.setMovedByUserName(stockMovement.getMovedBy().getFullName());
        }
        return dto;
    }
}
