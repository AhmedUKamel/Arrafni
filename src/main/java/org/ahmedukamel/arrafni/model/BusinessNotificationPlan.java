package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_notification_plan", uniqueConstraints =
@UniqueConstraint(name = "BUSINESS_NOTIFICATION_PLAN_UNIQUE_NAME_CONSTRAINT", columnNames = "name"))
public class BusinessNotificationPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean active = true;
}
