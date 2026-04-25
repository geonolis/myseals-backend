package com.myseals.controller;

import com.myseals.dto.StockMovementRequestDTO;
import com.myseals.dto.StockMovementResponseDTO;
import com.myseals.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {
        List<StockMovementResponseDTO> stockMovements = stockMovementService.findAll();
        return ResponseEntity.ok(stockMovements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(@PathVariable @NonNull UUID id) {
        UUID movementId = id;
        return stockMovementService.findById(movementId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(@Valid @RequestBody @NonNull StockMovementRequestDTO stockMovementRequestDTO) {
        StockMovementResponseDTO createdMovement = stockMovementService.createStockMovement(stockMovementRequestDTO);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable @NonNull UUID id) {
        UUID movementId = id;
        stockMovementService.deleteById(movementId);
        return ResponseEntity.noContent().build();
    }
}
