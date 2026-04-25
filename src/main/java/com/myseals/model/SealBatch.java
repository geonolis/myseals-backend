package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SealBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID batchId;

    @Column(nullable = false)
    private String manufacturer;

    @Column(unique = true, nullable = false, length = 100)
    private String batchNumber;

    @Column(nullable = false, length = 50)
    private String startSealNumber;

    @Column(nullable = false, length = 50)
    private String endSealNumber;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate receivedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by")
    private User registeredBy;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
