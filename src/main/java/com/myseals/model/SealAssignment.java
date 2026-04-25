package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SealAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID assignmentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seal_id", unique = true, nullable = false)
    private Seal seal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedToUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_office_id")
    private Office assignedToOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @Column(nullable = false)
    private OffsetDateTime assignmentDate;

    private OffsetDateTime returnDate;

    @Column(nullable = false)
    private String status; // e.g., ACTIVE, RETURNED, CANCELLED

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        assignmentDate = OffsetDateTime.now();
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
