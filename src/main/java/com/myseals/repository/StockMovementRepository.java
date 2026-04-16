package com.myseals.repository;

import com.myseals.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
}
