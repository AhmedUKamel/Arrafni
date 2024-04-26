package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories", uniqueConstraints =
@UniqueConstraint(name = "CATEGORY_NAME_UNIQUE_CONSTRAINT", columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "category_businesses")
    private Set<Business> businesses = new HashSet<>();
}