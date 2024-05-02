package org.ahmedukamel.arrafni.saver;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Component
public class FileSaver implements BiFunction<MultipartFile, Path, Optional<String>> {
    @Override
    public Optional<String> apply(MultipartFile file, Path path) {
        try {
            Assert.notNull(file, "Missing file.");

            Supplier<String> supplier = () -> "%s.%s".formatted(UUID.randomUUID(),
                    FilenameUtils.getExtension(file.getOriginalFilename()));

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String name;
            do {
                name = supplier.get();
            } while (Files.exists(path.resolve(name)));

            Files.copy(file.getInputStream(), path.resolve(name));

            return Optional.of(name);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}