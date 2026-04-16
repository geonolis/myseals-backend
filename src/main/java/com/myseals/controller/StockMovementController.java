package com.myseals.controller;

import com.myseals.dto.StockMovementRequestDTO;
import com.myseals.dto.StockMovementResponseDTO;
import com.myseals.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock-movements")
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {
        List<StockMovementResponseDTO> stockMovements = stockMovementService.findAll();
        return ResponseEntity.ok(stockMovements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(@PathVariable UUID id) {
        return stockMovementService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovementRequestDTO) {
        StockMovementResponseDTO createdStockMovement = stockMovementService.createStockMovement(stockMovementRequestDTO);
        return new ResponseEntity<>(createdStockMovement, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable UUID id) {
        stockMovementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
