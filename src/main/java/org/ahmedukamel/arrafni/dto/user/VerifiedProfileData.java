package org.ahmedukamel.arrafni.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.PhoneNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedProfileData {
    private String email;
    private PhoneNumber phoneNumber;
}