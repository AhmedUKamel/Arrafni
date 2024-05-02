package org.ahmedukamel.arrafni.service.db;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DatabaseService {
    public static <T, R> R get(Function<T, Optional<R>> function, T value, Class<?> theClass) {
        return function.apply(value).orElseThrow(
                () -> new EntityNotFoundException("Entity %s with identifier %s not found!".formatted(theClass.getSimpleName(), value)));
    }

    public static <T> void unique(Predicate<T> predicate, T value, Class<?> theClass) {
        if (predicate.test(value))
            throw new EntityExistsException("Entity %s with identifier %s already exists!".formatted(theClass.getSimpleName(), value));
    }
}