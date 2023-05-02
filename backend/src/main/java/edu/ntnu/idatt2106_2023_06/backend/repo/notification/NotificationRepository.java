package edu.ntnu.idatt2106_2023_06.backend.repo.notification;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    List<Notification> findByUser(User user);

    Optional<Object> findByUserAndFridgeItem_Item_ItemId(User user, Long itemId);

    List<Notification> findByFridgeItem(FridgeItems fridgeItem);
}
