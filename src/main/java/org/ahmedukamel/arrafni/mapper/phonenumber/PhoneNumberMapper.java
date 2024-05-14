package org.ahmedukamel.arrafni.mapper.phonenumber;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PhoneNumberMapper implements Function<String, PhoneNumber> {
    @Override
    public PhoneNumber apply(String s) {
        try {
            Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(s, "EG");
            return new PhoneNumber(phoneNumber.getCountryCode(), phoneNumber.getNationalNumber());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}