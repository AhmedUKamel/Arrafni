package org.ahmedukamel.arrafni.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PhoneNumber {
    private int countryCode;

    private long nationalNumber;

    @Override
    public String toString() {
        return "+%d%d".formatted(countryCode, nationalNumber);
    }
}