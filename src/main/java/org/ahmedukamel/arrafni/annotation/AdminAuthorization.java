package org.ahmedukamel.arrafni.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
public @interface AdminAuthorization {
}