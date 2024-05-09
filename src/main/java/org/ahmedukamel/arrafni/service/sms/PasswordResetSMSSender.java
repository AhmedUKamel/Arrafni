package org.ahmedukamel.arrafni.service.sms;

import org.ahmedukamel.arrafni.model.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetSMSSender implements SMSSender {
    @Override
    public void sendSMS(PhoneNumber phoneNumber, String... strings) {

    }
}