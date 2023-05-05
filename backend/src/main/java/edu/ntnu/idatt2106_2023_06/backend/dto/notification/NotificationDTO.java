package edu.ntnu.idatt2106_2023_06.backend.dto.notification;

import lombok.Builder;
import lombok.NonNull;

/**
 * This record represents a DTO for a notification.
 * @param notificationId    The ID of the notification
 * @param isRead            Whether the notification has been read or not
 * @param itemName          The name of the item the notification concerns
 * @param expirationDate    The expiration date of the item
 * @param fridgeId          The fridge all of this information pertains to
 */
@Builder
public record NotificationDTO(@NonNull Long notificationId,
                              @NonNull Boolean isRead,
                              @NonNull String itemName,
                              @NonNull String expirationDate,
                              @NonNull Long fridgeId) {
}