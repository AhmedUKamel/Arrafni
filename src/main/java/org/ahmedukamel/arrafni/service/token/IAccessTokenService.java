package org.ahmedukamel.arrafni.service.token;

import org.ahmedukamel.arrafni.model.User;

public interface IAccessTokenService {
    String generateToken(User user);

    User getUser(String token);

    void revokeToken(String token);

    void revokeTokens(User user);
}