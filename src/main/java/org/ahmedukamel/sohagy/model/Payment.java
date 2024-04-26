package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.sohagy.model.enumration.LicenceType;
import org.ahmedukamel.sohagy.model.enumration.PaymentStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments", uniqueConstraints =
@UniqueConstraint(name = "PAYMENT_CODE_UNIQUE_CONSTRAINT", columnNames = "code"))
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private BigDecimal cost;

    @Column(nullable = false, length = 16, columnDefinition = "varchar(16) default 'PENDING'")
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LicenceType licenceType;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime creation;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime deadline;

    @Column(updatable = false)
    private ZonedDateTime acceptance;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;
}