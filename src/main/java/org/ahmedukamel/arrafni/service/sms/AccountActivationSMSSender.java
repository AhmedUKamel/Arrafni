package org.ahmedukamel.arrafni.service.sms;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.SMSConstants;
import org.ahmedukamel.arrafni.model.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AccountActivationSMSSender implements SMSSender {
    @Value(value = "${application.sms.api}")
    String api;

    @Value(value = "${application.sms.username}")
    String username;

    @Value(value = "${application.sms.password}")
    String password;

    @Value(value = "${application.sms.sender}")
    String sender;

    RestTemplate restTemplate = new RestTemplate();

    final MessageSource messageSource;

    @Override
    public void sendSMS(PhoneNumber phoneNumber, String... strings) {
//        if (strings.length != 1) {
//            throw new IllegalArgumentException("Wrong number of arguments. Expected 1 but got " + strings.length);
//        }
//        String code = strings[0];
//        String message = messageSource.getMessage("sms.account.activation.message", new String[]{code}, new Locale("ar"));
//
//        MultiValueMap<String, List<String>> params = new LinkedMultiValueMap<>();
//        params.add(SMSConstants.USERNAME, Collections.singletonList(username));
//        params.add(SMSConstants.PASSWORD, Collections.singletonList(password));
//        params.add(SMSConstants.SENDER, Collections.singletonList(sender));
//        params.add(SMSConstants.MESSAGE, Collections.singletonList(message));
//        params.add(SMSConstants.MOBILE, Collections.singletonList(phoneNumber.toString()));
//        params.add(SMSConstants.ENVIRONMENT, Collections.singletonList(SMSConstants.ENVIRONMENT_VALUE));
//        params.add(SMSConstants.LANGUAGE, Collections.singletonList(SMSConstants.LANGUAGE_VALUE));
//        params.add(SMSConstants.DELAY_UNIT, Collections.singletonList(SMSConstants.DELAY_UNIT_VALUE));
//
//        ResponseEntity<String> response = restTemplate.getForEntity(api, String.class, params);
//        System.out.println(response.getStatusCode());
        System.out.println("send sms");
    }
}