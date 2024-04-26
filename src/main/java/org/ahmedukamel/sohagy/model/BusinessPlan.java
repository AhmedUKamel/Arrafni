package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "business_plans", uniqueConstraints =
@UniqueConstraint(name = "PLAN_NAME_UNIQUE_CONSTRAINT", columnNames = "name"))
public class BusinessPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean active = true;

    @OneToMany(mappedBy = "plan")
    private Set<BusinessLicence> licences = new HashSet<>();
}