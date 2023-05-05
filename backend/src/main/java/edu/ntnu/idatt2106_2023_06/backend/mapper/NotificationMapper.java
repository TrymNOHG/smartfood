package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.notification.NotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 The NotificationMapper class contains methods for mapping between Notification model and DTO.
 */
public class NotificationMapper {

    /**
     * This method converts a list of notifications to a list of notificationDTOs.
     * @param notifications    List of Notification objects
     * @return                  List of NotificationDTO objects
     */
    public static List<NotificationDTO> toNotificationDTO(List<Notification> notifications) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(Notification notification : notifications) {
            notificationDTOS.add(toNotificationDTO(notification));
        }
        return notificationDTOS;
    }

    /**
     * This method converts a Notification object to a NotificationDTO object.
     * @param notification      Notification object to be converted
     * @return                  NotificationDTO object
     */
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder().expirationDate(notification.getFridgeItem().getExpirationDate().toString())
                .isRead(notification.getIsRead())
                .itemName(notification.getFridgeItem().getItem().getProductName())
                .notificationId(notification.getNotificationId())
                .fridgeId(notification.getFridgeItem().getFridge().getFridgeId())
                .build();
    }
}
