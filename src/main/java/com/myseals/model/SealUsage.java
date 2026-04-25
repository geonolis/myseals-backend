package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_usage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SealUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID usageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seal_id", nullable = false)
    private Seal seal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_by")
    private User usedBy;

    @Column(nullable = false)
    private String usageLocation;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String documentReference;
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private OffsetDateTime usedAt;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        usedAt = OffsetDateTime.now();
        createdAt = OffsetDateTime.now();
    }
}
