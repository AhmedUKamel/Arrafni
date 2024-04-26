package org.ahmedukamel.sohagy.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.ahmedukamel.sohagy.constant.JwtConstants;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.service.token.IAccessTokenService;
import org.ahmedukamel.sohagy.service.token.JsonWebTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class CustomLogoutHandler implements LogoutHandler {
    public final IAccessTokenService service;

    public CustomLogoutHandler(JsonWebTokenService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String message = "Missing Authorization.";
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasLength(authorizationHeader)) {

            if (authorizationHeader.startsWith(JwtConstants.HEADER_PREFIX)) {
                final String token = authorizationHeader.substring(JwtConstants.HEADER_PREFIX.length());

                try {
                    service.revokeToken(token);
                    return;
                } catch (Exception exception) {
                    message = exception.getMessage();
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), new ApiResponse(false, message, ""));
    }
}