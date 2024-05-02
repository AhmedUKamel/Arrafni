package org.ahmedukamel.arrafni.service.notification;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.UserNotificationResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.notification.UserNotificationResponseMapper;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.UserNotification;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

@Service
@RequiredArgsConstructor
public class UserNotificationService implements IUserNotificationService {
    final UserNotificationResponseMapper mapper;
    final UserRepository repository;

    @Override
    public void readNotification(Long id) {
        UserNotification notification = getUserNotification(id);
        notification.setRead(true);
        repository.save(ContextHolderUtils.getUser());
    }

    @Override
    public void deleteNotification(Long id) {
        UserNotification notification = getUserNotification(id);
        User user = ContextHolderUtils.getUser();
        user.getNotifications().remove(notification);
        repository.save(user);
    }

    @Override
    public Object getNotification(Long id) {
        UserNotification notification = getUserNotification(id);
        UserNotificationResponse response = mapper.apply(notification);
        return new ApiResponse(true, "Successful Get User Notification.", response);
    }

    @Override
    public Object getNotifications(long pageSize, long pageNumber, NotificationType type) {
        Predicate<UserNotification> filter = userNotification -> !Objects.nonNull(type) || userNotification.getNotification().getType().equals(type);
        ToLongFunction<UserNotification> comp = notification -> notification.getNotification().getId();
        List<UserNotificationResponse> response = ContextHolderUtils.getUser()
                .getNotifications()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparingLong(comp))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get User Notifications.", response);
    }

    private static UserNotification getUserNotification(Long id) {
        Function<Long, Optional<UserNotification>> function = notificationId -> ContextHolderUtils.getUser()
                .getNotifications()
                .stream()
                .filter(i -> i.getNotification().getId().equals(notificationId))
                .findFirst();
        return DatabaseService.get(function, id, UserNotification.class);
    }
}