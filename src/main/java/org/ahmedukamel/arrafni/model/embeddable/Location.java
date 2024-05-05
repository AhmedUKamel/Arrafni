package org.ahmedukamel.arrafni.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {
    private Double latitude;

    private Double longitude;
}