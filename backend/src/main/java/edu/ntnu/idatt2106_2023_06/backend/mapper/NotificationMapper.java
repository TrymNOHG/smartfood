package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.notification.NotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {
    public static List<NotificationDTO> toNotificationDTO(List<Notification> notifications) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(Notification notification : notifications) {
            notificationDTOS.add(toNotificationDTO(notification));
        }
        return notificationDTOS;
    }

    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder().expirationDate(notification.getFridgeItem().getExpirationDate().toString())
                .isRead(notification.getIsRead())
                .itemName(notification.getFridgeItem().getItem().getProductName())
                .notificationId(notification.getNotificationId())
                .build();
    }
}
