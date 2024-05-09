package org.ahmedukamel.arrafni.service.sms;

import org.ahmedukamel.arrafni.model.PhoneNumber;

public interface SMSSender {
    void sendSMS(PhoneNumber phoneNumber, String... strings);
}