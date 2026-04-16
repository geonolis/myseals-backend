package com.myseals.repository;

import com.myseals.model.SealUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SealUsageRepository extends JpaRepository<SealUsage, UUID> {
}
