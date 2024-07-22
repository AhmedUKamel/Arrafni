package org.ahmedukamel.arrafni.saver.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.business.SendBusinessNotificationRequest;
import org.ahmedukamel.arrafni.model.*;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;
import org.ahmedukamel.arrafni.repository.BusinessNotificationRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.DeviceTokenRepository;
import org.ahmedukamel.arrafni.repository.NotificationRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.service.firebase.FirebaseNotifier;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SendBusinessNotificationRequestSaver
        implements BiConsumer<SendBusinessNotificationRequest, MultipartFile> {

    private final BusinessNotificationRepository businessNotificationRepository;
    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository tokenRepository;
    private final BusinessRepository businessRepository;
    private final FirebaseNotifier firebaseNotifier;

    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void accept(SendBusinessNotificationRequest request, MultipartFile file) {
        Business business = DatabaseService.get(businessRepository::findByIdAndDeletedIsFalse,
                request.businessId(), Business.class);

        User currentUser = ContextHolderUtils.getUser();

        if (!business.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not owner of this business");
        }

        if (!business.isActive()) {
            throw new IllegalArgumentException("Business is not activated");
        }

        if (business.getNotificationCount() <= 0) {
            throw new IllegalArgumentException("Business does not have any notifications credits");
        }

        String image = null;
        if (Objects.nonNull(file) && !file.isEmpty()) {

            String filename = file.getOriginalFilename();

            Supplier<String> imageSupplier = () -> "%s.%s".formatted(
                    UUID.randomUUID(), FilenameUtils.getExtension(filename));
            try {
                if (!Files.exists(PathConstants.BUSINESS_NOTIFICATION_PICTURE)) {
                    Files.createDirectories(PathConstants.BUSINESS_NOTIFICATION_PICTURE);
                }

                Path filepath;
                do {
                    image = imageSupplier.get();
                    filepath = PathConstants.BUSINESS_NOTIFICATION_PICTURE.resolve(image);
                } while (Files.exists(filepath));

                Files.copy(file.getInputStream(), filepath);
            } catch (IOException exception) {
                throw new RuntimeException("Failed save image", exception);
            }
        }

        BusinessNotification businessNotification = new BusinessNotification();
        businessNotification.setTitle(request.title().strip());
        businessNotification.setBody(request.body().strip());
        businessNotification.setImage(image);
        businessNotification.setBusiness(business);

        var savedBusinessNotification = businessNotificationRepository.save(businessNotification);
        var tokens = tokenRepository.findAllByRegion(business.getRegion())
                .parallelStream()
                .map(DeviceToken::getToken)
                .toList();

        business.decrementNotificationCount();
        businessRepository.save(business);

        CompletableFuture
                .supplyAsync(() -> getNotification(savedBusinessNotification, business.getRegion().getUsers()), executor)
                .thenRunAsync(() -> firebaseNotifier.accept(savedBusinessNotification, tokens), executor);
    }

    private Notification getNotification(BusinessNotification businessNotification, Collection<User> users) {
        Notification notification = new Notification();
        notification.setMessage(businessNotification.getTitle());
        notification.setType(NotificationType.BUSINESS);
        notification.setTimestamp(LocalDateTime.now());
        notification.setBusinessNotification(businessNotification);

        Set<UserNotification> userNotifications = users
                .stream()
                .map(user -> new UserNotification(null, user, notification, false))
                .collect(Collectors.toSet());

        notification.setUsers(userNotifications);

        return notificationRepository.save(notification);
    }
}