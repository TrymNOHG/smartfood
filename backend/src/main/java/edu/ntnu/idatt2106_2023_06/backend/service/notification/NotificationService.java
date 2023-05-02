package edu.ntnu.idatt2106_2023_06.backend.service.notification;

import edu.ntnu.idatt2106_2023_06.backend.dto.notification.UpdateNotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.NotificationNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.notification.NotificationRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final FridgeService fridgeService;
    private final UserInfoService userInfoService;

    private final FridgeRepository fridgeRepository;
    private final FridgeItemsRepository fridgeItemsRepository;
    private final ItemService itemService;
    private final NotificationRepository notificationRepository;

    /**
     * Updates all notifications for a user in a fridge. This is done by checking if any items in the fridge
     * are about to expire, and if so, creating a notification for the user.
     *
     * @param fridgeId The id of the fridge to update notifications for.
     */
    public void updateNotifications(Long fridgeId) {
        checkNotification(fridgeId);
        User user = userInfoService.getAuthenticatedUserObject();
        List<FridgeItems> items = fridgeItemsRepository.findAllByFridge_FridgeId(fridgeId).orElseThrow(
                () -> new IllegalArgumentException("No items in fridge")
        );
        for (FridgeItems item : items) {
            if(item.getExpirationDate().isBefore(LocalDateTime.now().plusDays(1))
                    && notificationRepository.findByUserAndFridgeItem_Item_ItemId(
                            user, item.getItem().getItemId()).isEmpty()) {
                Notification notification = Notification.builder()
                        .fridgeItem(item)
                        .user(user)
                        .isRead(false)
                        .build();
                notificationRepository.save(notification);
            }
        }
    }

    /**
     * Get all notifications of a user. Uses the JWT token to get the user.
     */
    public List<Notification> getAllNotifications() {
        if(!userInfoService.isAuthenticated())
            throw new UnauthorizedException(userInfoService.getAuthenticatedUserUsername());
        User user = userInfoService.getAuthenticatedUserObject();
        return notificationRepository.findByUser(user);
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
        List<Notification> notifications = getAllNotifications();
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }

    /**
     * Deletes a notification for every user in a fridge.
     * This method should be called when removing an item from a fridge.
     *
     * @param notification The notification to delete.
     */
    public void deleteNotificationForEveryUserInFridge(Notification notification) {
        checkNotification(notification.getFridgeItem().getFridge().getFridgeId());
        List<Notification> notifications = notificationRepository.findByFridgeItem(notification.getFridgeItem());
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
