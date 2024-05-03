package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sub_categories", uniqueConstraints =
@UniqueConstraint(name = "SUB_CATEGORY_NAME_UNIQUE_CONSTRAINT", columnNames = "name"))
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String logo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MainCategory mainCategory;

    @ManyToMany(mappedBy = "subCategories", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Business> businesses = new HashSet<>();
}