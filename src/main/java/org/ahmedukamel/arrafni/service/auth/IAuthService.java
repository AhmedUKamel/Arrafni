package org.ahmedukamel.arrafni.service.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    Object register(Object object);

    Object login(String username, String password, HttpServletRequest httpServletRequest);

    Object forgetPassword(String username);

    Object activateAccount(String username);
}