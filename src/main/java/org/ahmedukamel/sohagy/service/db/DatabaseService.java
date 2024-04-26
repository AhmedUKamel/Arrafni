package org.ahmedukamel.sohagy.service.db;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.ahmedukamel.sohagy.constant.ExceptionConstants;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DatabaseService {
    public static <T, R> R get(Function<T, Optional<R>> function, T value, Class<?> theClass) {
        return function.apply(value).orElseThrow(
                () -> new EntityNotFoundException(ExceptionConstants.EntityNotFound.formatted(value, theClass.getSimpleName())));
    }

    public static <T> void unique(Predicate<T> predicate, T value, Class<?> theClass) {
        if (predicate.test(value))
            throw new EntityExistsException(ExceptionConstants.EntityExists.formatted(value, theClass.getSimpleName()));
    }
}