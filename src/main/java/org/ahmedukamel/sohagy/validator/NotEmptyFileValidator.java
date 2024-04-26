package org.ahmedukamel.sohagy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ahmedukamel.sohagy.annotation.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmpty, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(file) && !file.isEmpty();
    }
}