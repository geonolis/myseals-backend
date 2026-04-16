package com.myseals.repository;

import com.myseals.model.Seal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SealRepository extends JpaRepository<Seal, UUID> {
    Optional<Seal> findBySealNumber(String sealNumber);
}
