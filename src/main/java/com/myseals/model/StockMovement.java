package com.myseals.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
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

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        movementDate = OffsetDateTime.now();
        createdAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getMovementId() {
        return movementId;
    }

    public void setMovementId(UUID movementId) {
        this.movementId = movementId;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Office getFromOffice() {
        return fromOffice;
    }

    public void setFromOffice(Office fromOffice) {
        this.fromOffice = fromOffice;
    }

    public Office getToOffice() {
        return toOffice;
    }

    public void setToOffice(Office toOffice) {
        this.toOffice = toOffice;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getMovedBy() {
        return movedBy;
    }

    public void setMovedBy(User movedBy) {
        this.movedBy = movedBy;
    }

    public OffsetDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(OffsetDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
