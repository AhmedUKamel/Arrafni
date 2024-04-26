package org.ahmedukamel.sohagy.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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