package com.myseals.repository;

import com.myseals.model.SealBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SealBatchRepository extends JpaRepository<SealBatch, UUID> {
    Optional<SealBatch> findByBatchNumber(String batchNumber);
}
