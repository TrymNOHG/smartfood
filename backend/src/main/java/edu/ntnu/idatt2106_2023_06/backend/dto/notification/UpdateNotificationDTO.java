package edu.ntnu.idatt2106_2023_06.backend.dto.notification;

import lombok.NonNull;

/**
 * This record represents a DTO for updating a notification.
 * @param notificationId    The ID of the notification to update.
 */
public record UpdateNotificationDTO(@NonNull Long notificationId) {
}
