package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * This exception is thrown when a notification can not be found.
 */
public class NotificationNotFoundException extends NotFoundException {

    /**
     * This method constructs a new NotificationNotFoundException with the given notification ID.
     *
     * @param notificationId The ID of the notification that was searched for, given as a Long value
     */
    public NotificationNotFoundException(Long notificationId) {
        super("Notification", notificationId);
    }
}
