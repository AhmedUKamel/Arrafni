package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.embeddable.Location;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "regions", uniqueConstraints = {
        @UniqueConstraint(name = "REGION_LOCATION_UNIQUE_CONSTRAINT", columnNames = {"latitude", "longitude"}),
        @UniqueConstraint(name = "REGION_NAME_UNIQUE_CONSTRAINT", columnNames = "name")
})
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "latitude", column = @Column(nullable = false)),
            @AttributeOverride(name = "longitude", column = @Column(nullable = false))
    })
    private Location location;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private City city;

    @OneToMany(mappedBy = "region")
    private Set<Business> businesses = new HashSet<>();
}