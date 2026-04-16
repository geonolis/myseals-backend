package com.myseals.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seal_assignments")
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

    @Column(nullable = false)
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

    // Getters and Setters
    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public Office getAssignedToOffice() {
        return assignedToOffice;
    }

    public void setAssignedToOffice(Office assignedToOffice) {
        this.assignedToOffice = assignedToOffice;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public OffsetDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(OffsetDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(OffsetDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
