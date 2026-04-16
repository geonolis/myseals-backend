package com.myseals.repository;

import com.myseals.model.SealAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SealAssignmentRepository extends JpaRepository<SealAssignment, UUID> {
    Optional<SealAssignment> findBySealSealId(UUID sealId);
}
