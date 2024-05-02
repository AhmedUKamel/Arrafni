package org.ahmedukamel.arrafni.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ahmedukamel.arrafni.validator.SingleSocialLinkValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = SingleSocialLinkValidator.class)
public @interface SingleSocialLink {
    String message() default "Business must contains at most one link for each social platform.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}