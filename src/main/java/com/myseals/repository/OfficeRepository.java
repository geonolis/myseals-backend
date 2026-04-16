package com.myseals.repository;

import com.myseals.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OfficeRepository extends JpaRepository<Office, UUID> {
    Optional<Office> findByOfficeCode(String officeCode);
}
