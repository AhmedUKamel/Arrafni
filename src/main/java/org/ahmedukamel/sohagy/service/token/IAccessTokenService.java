package org.ahmedukamel.sohagy.service.token;

import org.ahmedukamel.sohagy.model.User;

public interface IAccessTokenService {
    String generateToken(User user);

    User getUser(String token);

    void revokeToken(String token);

    void revokeTokens(User user);
}