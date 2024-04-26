package org.ahmedukamel.sohagy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ahmedukamel.sohagy.annotation.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class NotEmptyFilesValidator implements ConstraintValidator<NotEmpty, MultipartFile[]> {
    @Override
    public boolean isValid(MultipartFile[] files, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(files).noneMatch(MultipartFile::isEmpty);
    }
}