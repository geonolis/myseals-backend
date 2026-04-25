package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sealId;

    @Column(unique = true, nullable = false, length = 50)
    private String sealNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private SealBatch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SealStatus currentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_office_id")
    private Office currentOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedToUser;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime registeredAt;

    @Column(nullable = false)
    private OffsetDateTime lastUpdatedAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = OffsetDateTime.now();
        lastUpdatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = OffsetDateTime.now();
    }
}
