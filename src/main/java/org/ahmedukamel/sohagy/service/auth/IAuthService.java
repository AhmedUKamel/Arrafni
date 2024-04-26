package org.ahmedukamel.sohagy.service.auth;

public interface IAuthService {
    Object register(Object object);

    Object login(String username, String password);

    Object forgetPassword(String username);

    Object activateAccount(String username);
}