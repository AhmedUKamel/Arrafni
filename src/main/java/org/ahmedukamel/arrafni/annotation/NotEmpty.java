package org.ahmedukamel.arrafni.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ahmedukamel.arrafni.validator.NotEmptyFileValidator;
import org.ahmedukamel.arrafni.validator.NotEmptyFilesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
@Constraint(validatedBy = {NotEmptyFileValidator.class, NotEmptyFilesValidator.class})
public @interface NotEmpty {
    String message() default "File is empty.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}