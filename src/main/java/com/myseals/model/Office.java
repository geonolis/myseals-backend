package com.myseals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "offices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID officeId;

    @Column(nullable = false)
    private String officeName;

    @Column(unique = true, nullable = false, length = 50)
    private String officeCode;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String contactEmail;
    private String contactPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_office_id")
    private Office parentOffice;

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
