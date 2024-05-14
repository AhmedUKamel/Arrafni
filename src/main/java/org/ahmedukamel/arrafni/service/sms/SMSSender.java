package org.ahmedukamel.arrafni.service.sms;

import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;

public interface SMSSender {
    void sendSMS(PhoneNumber phoneNumber, String... strings);
}