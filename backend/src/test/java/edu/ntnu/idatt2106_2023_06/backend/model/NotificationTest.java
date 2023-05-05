package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private final User user = new User( "Ole123", "Ole", "Norman",
                                                            "password", "Ole@gmail.com");
    private final Item item = new Item();
    private final Fridge fridge = new Fridge();
    private final FridgeItems fridgeItem = FridgeItems.builder().item(item).fridge(fridge).purchaseDate(LocalDateTime.now())
            .expirationDate(LocalDateTime.now()).build();

    @Test
    void testCreateNotification() {
        Notification notification = Notification.builder()
                .isRead(false)
                .createdDate(LocalDateTime.now())
                .user(user)
                .fridgeItem(fridgeItem)
                .build();

        assertNotNull(notification);
        assertNull(notification.getNotificationId());
        assertFalse(notification.getIsRead());
        assertNotNull(notification.getCreatedDate());
        assertEquals(user, notification.getUser());
        assertEquals(fridgeItem, notification.getFridgeItem());
    }

    @Test
    void testUpdateNotification() {
        Notification notification = Notification.builder()
                .isRead(false)
                .createdDate(LocalDateTime.now())
                .user(user)
                .fridgeItem(fridgeItem)
                .build();

        LocalDateTime newCreatedDate = LocalDateTime.now().minusDays(1);
        notification.setIsRead(true);
        notification.setCreatedDate(newCreatedDate);

        assertTrue(notification.getIsRead());
        assertEquals(newCreatedDate, notification.getCreatedDate());
    }

    @Test
    public void testGetterAndSetter() {
        Notification notification = new Notification();
        notification.setNotificationId(1L);
        notification.setIsRead(true);
        notification.setCreatedDate(LocalDateTime.now());
        User user = new User();
        Fridge fridge = new Fridge();
        notification.setUser(user);
        notification.setFridgeItem(fridgeItem);

        assertEquals(notification.getNotificationId(), Long.valueOf(1L));
        assertTrue(notification.getIsRead());
        assertNotNull(notification.getCreatedDate());
        assertEquals(notification.getUser(), user);
        assertEquals(notification.getFridgeItem(), fridgeItem);
    }

    @Test
    public void testNoArgsConstructor() {
        Notification notification = new Notification();
        assertNull(notification.getNotificationId());
        assertNull(notification.getIsRead());
        assertNull(notification.getCreatedDate());
        assertNull(notification.getUser());
        assertNull(notification.getFridgeItem());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime dateTime = LocalDateTime.now();
        User user = new User();
        Fridge fridge = new Fridge();
        Notification notification = new Notification(1L, true, dateTime, user, fridgeItem);

        assertEquals(notification.getNotificationId(), Long.valueOf(1L));
        assertTrue(notification.getIsRead());
        assertEquals(notification.getCreatedDate(), dateTime);
        assertEquals(notification.getUser(), user);
        assertEquals(notification.getFridgeItem(), fridgeItem);
    }

    @Test
    public void testBuilder() {
        LocalDateTime dateTime = LocalDateTime.now();
        User user = new User();
        Fridge fridge = new Fridge();
        Notification notification = Notification.builder()
                .notificationId(1L)
                .isRead(true)
                .createdDate(dateTime)
                .user(user)
                .fridgeItem(fridgeItem)
                .build();

        assertEquals(notification.getNotificationId(), Long.valueOf(1L));
        assertTrue(notification.getIsRead());
        assertEquals(notification.getCreatedDate(), dateTime);
        assertEquals(notification.getUser(), user);
        assertEquals(notification.getFridgeItem(), fridgeItem);
    }
}

