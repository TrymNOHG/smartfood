package edu.ntnu.idatt2106_2023_06.backend.service;

import edu.ntnu.idatt2106_2023_06.backend.dto.notification.NotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.notification.UpdateNotificationDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.NotificationNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.notification.NotificationRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.notification.NotificationService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class NotificationServiceTest {


    @Nested
    @SpringBootTest
    class UpdateNotifications{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            assertDoesNotThrow(() -> notificationService.updateNotifications(1L));
        }

    }

    @Nested
    @SpringBootTest
    class UpdateAllNotifications{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            assertDoesNotThrow(() -> notificationService.updateAllNotifications());
        }

    }

    @Nested
    @SpringBootTest
    class GetAllNotificationsOfSingeUser{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void gets_notification_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            List<Notification> notificationList = notificationService.getAllNotificationsOfSingeUser();
            assertEquals(user, notificationList.get(0).getUser());
        }

    }

    @Nested
    @SpringBootTest
    class GetAllNotificationsOfSingeUserAsDTO{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void gets_notificationDTO_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            List<NotificationDTO> notificationList = notificationService.getAllNotificationsOfSingeUserAsDTO();
            assertEquals("Tine Melk", notificationList.get(0).itemName());
        }

    }

    @Nested
    @SpringBootTest
    class SetNotificationAsRead{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void gets_notificationDTO_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            UpdateNotificationDTO updateNotificationDTO = new UpdateNotificationDTO(1L);
            notificationService.setNotificationAsRead(updateNotificationDTO);
            Notification notification1 = notificationRepository.getReferenceById(1L);
            assertEquals(true, notification1.getIsRead());
        }

    }

    @Nested
    @SpringBootTest
    class SetAllNotificationsAsRead{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void gets_notificationDTO_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            notificationService.setAllNotificationsAsRead();
            Notification notification1 = notificationRepository.getReferenceById(1L);
            assertEquals(true, notification1.getIsRead());
        }

    }

    @Nested
    @SpringBootTest
    class DeleteNotification{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void deletes_notification_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            notificationService.deleteNotification(1L);
            assertTrue(notificationRepository.findByFridgeItem(fridgeItems).isEmpty());
        }

    }

    @Nested
    @SpringBootTest
    class DeleteAllNotificationsForOneUser{
        @Autowired
        NotificationService notificationService;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        NotificationRepository notificationRepository;

        @Test
        @Transactional
        void deletes_notification_for_correct_user(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);

            Notification notification = new Notification(false, LocalDateTime.now(), user, fridgeItems);
            notificationRepository.save(notification);
            notificationService.deleteAllNotificationsForOneUser(1L);
            assertTrue(notificationRepository.findByFridgeItem(fridgeItems).isEmpty());
        }

    }
}
