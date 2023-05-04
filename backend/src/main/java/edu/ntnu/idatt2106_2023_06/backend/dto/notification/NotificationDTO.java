package edu.ntnu.idatt2106_2023_06.backend.dto.notification;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record NotificationDTO(@NonNull Long notificationId,
                              @NonNull Boolean isRead,
                              @NonNull String itemName,
                              @NonNull String expirationDate) {
}