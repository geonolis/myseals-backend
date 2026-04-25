package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID movementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seal_id", nullable = false)
    private Seal seal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_office_id")
    private Office fromOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_office_id")
    private Office toOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moved_by", nullable = false)
    private User movedBy;

    @Column(nullable = false)
    private OffsetDateTime movementDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        movementDate = OffsetDateTime.now();
        createdAt = OffsetDateTime.now();
    }
}
