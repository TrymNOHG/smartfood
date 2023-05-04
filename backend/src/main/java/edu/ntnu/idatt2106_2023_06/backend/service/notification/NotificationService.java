package edu.ntnu.idatt2106_2023_06.backend.service.notification;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.notification.NotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.notification.UpdateNotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeItemsNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.NotificationNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.NotificationMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.notification.NotificationRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final FridgeService fridgeService;
    private final UserInfoService userInfoService;

    private final FridgeItemsRepository fridgeItemsRepository;
    private final NotificationRepository notificationRepository;

    /**
     * Updates all notifications of a user.
     */
    public void updateAllNotifications() {
        List<Long> fridges = fridgeService.retrieveFridgeIdsByUsername(userInfoService.getAuthenticatedUserUsername());
        for (Long fridgeId : fridges) {
            updateNotifications(fridgeId);
        }
    }

    /**
     * Updates all notifications for a user in a fridge. This is done by checking if any items in the fridge
     * are about to expire, and if so, creating a notification for the user.
     *
     * @param fridgeId The id of the fridge to update notifications for.
     */
    private void updateNotifications(Long fridgeId) {
        checkNotification(fridgeId);
        User user = userInfoService.getAuthenticatedUserObject();
        List<FridgeItems> items = fridgeItemsRepository.findAllByFridge_FridgeId(fridgeId).orElseThrow(
                () -> new FridgeItemsNotFoundException(fridgeId)
        );
        for (FridgeItems item : items) {
            if(item.getExpirationDate().isBefore(LocalDateTime.now().plusDays(2))
                    && notificationRepository.findByUserAndFridgeItem_Item_ItemId(
                            user, item.getItem().getItemId()).isEmpty()) {
                Notification notification = Notification.builder()
                        .fridgeItem(item)
                        .user(user)
                        .isRead(false)
                        .createdDate(LocalDateTime.now())
                        .build();
                notificationRepository.save(notification);
            }
        }
    }

    /**
     * Get all notifications of a user. Uses the JWT token to get the user.
     */
    public List<Notification> getAllNotificationsOfSingeUser() {
        if(!userInfoService.isAuthenticated())
            throw new UnauthorizedException(userInfoService.getAuthenticatedUserUsername());
        User user = userInfoService.getAuthenticatedUserObject();
        return notificationRepository.findByUser(user);
    }

    /**
     * Get all notifications of a user as DTOs. Uses the JWT token to get the user.
     *
     * @return A list of notifications as DTOs.
     */
    public List<NotificationDTO> getAllNotificationsOfSingeUserAsDTO() {
        List<Notification> notifications = getAllNotificationsOfSingeUser();
        notifications.sort(Comparator.comparing(Notification::getCreatedDate));
        return NotificationMapper.toNotificationDTO(notifications);
    }

    /**
     * Sets a notification as read.
     *
     * @param updateNotificationDTO The notification to set as read.
     */
    public void setNotificationAsRead(UpdateNotificationDTO updateNotificationDTO) {
        Notification notification = notificationRepository.findById(updateNotificationDTO.notificationId()).orElseThrow(
                () -> new NotificationNotFoundException(updateNotificationDTO.notificationId())
        );
        checkNotification(notification.getFridgeItem().getFridge().getFridgeId());
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Sets all notifications for a user as read.
     */
    public void setAllNotificationsAsRead() {
        List<Notification> notifications = getAllNotificationsOfSingeUser();
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }

    /**
     * Deletes a notification for every user in a fridge.
     * This method should be called when removing an item from a fridge.
     *
     * @param itemRemoveDTO The item to delete.
     */
    public void deleteNotificationForEveryUserInFridge(ItemRemoveDTO itemRemoveDTO) {
        checkNotification(itemRemoveDTO.fridgeId());
        FridgeItems fridgeItem = fridgeItemsRepository
                .findByItem_ProductNameAndItem_Store_StoreNameAndFridge_FridgeId(
                        itemRemoveDTO.itemName(),
                        itemRemoveDTO.store(),
                        itemRemoveDTO.fridgeId()).orElseThrow(
                () -> new FridgeItemsNotFoundException(itemRemoveDTO.itemName())
        );
        if (itemRemoveDTO.quantity() != fridgeItem.getAmount())
            return;
        List<Notification> notifications = notificationRepository.findByFridgeItem(fridgeItem);
        notificationRepository.deleteAll(notifications);
    }

    /**
     * Deletes all notifications for a user in a fridge.
     *
     * @param fridgeId The id of the fridge to delete notifications for.
     */
    public void deleteAllNotificationsForOneUser(Long fridgeId) {
        checkNotification(fridgeId);
        User user = userInfoService.getAuthenticatedUserObject();
        List<Notification> notifications = notificationRepository.findByUser(user);
        notificationRepository.deleteAll(notifications);
    }

    /**
     * Deletes a notification. for a user.
     *
     * @param notificationId The id of the notification to delete.
     */
    public void deleteNotification(Long notificationId) {
        if(!userInfoService.isAuthenticated())
            throw new UnauthorizedException(userInfoService.getAuthenticatedUserUsername());
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotificationNotFoundException(notificationId)
        );
        checkNotification(notification.getFridgeItem().getFridge().getFridgeId());
        notificationRepository.delete(notification);
    }

    /**
     * Checks if a user is authenticated and exists in a fridge.
     *
     * @param fridgeId The id of the fridge to check.
     */
    private void checkNotification(Long fridgeId) {
        if(!userInfoService.isAuthenticated())
            throw new UnauthorizedException(userInfoService.getAuthenticatedUserUsername());
        fridgeService.userExistsInFridge(fridgeId, userInfoService.getAuthenticatedUserUsername());
    }
}
