package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFound;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class FridgeServiceIntegrationTest {



    @Nested
    @SpringBootTest
    class Fridge_can_be{
        @Autowired
        UserRepository userRepository;

        @Autowired
        FridgeRepository fridgeRepository;


        @Autowired
        FridgeService fridgeService;

        @Autowired
        UserService userService;


        void populateDB() {
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());
        }


//        @Test
//        void added_to_database(){
//            User user = User
//                    .builder()
//                    .userId(1L)
//                    .username("OleN")
//                    .password("password")
//                    .firstName("Ole")
//                    .lastName("Norman")
//                    .email("test@gamil.com")
//                    .build();
//
//            userRepository.save(user);
//            fridgeService.initializeFridge(user.getUsername());
//
//            Fridge expectedFridge = new Fridge(1L, "Home Fridge",
//                    new HashSet<>(), new ArrayList<>(), new ArrayList<>());
//
//            Fridge actualFridge = fridgeRepository.findByFridgeId(1L)
//                    .orElseThrow(() -> new FridgeNotFound(1L));
//
//            Assertions.assertEquals(expectedFridge, actualFridge);
//        }

    }

    @Nested
    @SpringBootTest
    class A_Fridge_Member_can_be {

        @Autowired
        UserRepository userRepository;

        @Autowired
        FridgeRepository fridgeRepository;


        @Autowired
        FridgeService fridgeService;

        @Autowired
        UserService userService;
        @Autowired
        private FridgeMemberRepository fridgeMemberRepository;


        User populateDB() {
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            return user;
        }


//        @Test
//        public void added_to_database_as_super_user_by_super_user(){
//            //This user is automatically superuser, since they started the fridge
//            User user = populateDB();
//            Fridge fridge = fridgeRepository.findByFridgeId(1L)
//                    .orElseThrow(() -> new FridgeNotFound(1L));
//
//            User newUser = User
//                    .builder()
//                    .userId(2L)
//                    .username("Hans123")
//                    .password("password")
//                    .firstName("Hans")
//                    .lastName("Norman")
//                    .email("test@gamil.com")
//                    .memberships(new HashSet<>())
//                    .build();
//
//
//            userRepository.save(newUser);
//
//            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
//                    .builder()
//                    .fridgeId(1L)
//                    .isSuperUser(true)
//                    .username(newUser.getUsername())
//                    .build();
//
//            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());
//
//
//            //TODO: test that exception is not thrown
//
//            FridgeMember fridgeMember = fridgeMemberRepository
//                    .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
//                            .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));
//
//            Assertions.assertTrue(fridgeMember.isSuperUser());
//
//
//        }
//
//        @Test
//        public void added_to_database_as_regular_user_by_super_user(){
//            //This user is automatically superuser, since they started the fridge
//            User user = populateDB();
//            Fridge fridge = fridgeRepository.findByFridgeId(1L)
//                    .orElseThrow(() -> new FridgeNotFound(1L));
//
//            User newUser = User
//                    .builder()
//                    .userId(2L)
//                    .username("Hans123")
//                    .password("password")
//                    .firstName("Hans")
//                    .lastName("Norman")
//                    .email("test@gamil.com")
//                    .build();
//
//            userRepository.save(newUser);
//
//            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
//                    .builder()
//                    .fridgeId(1L)
//                    .isSuperUser(false)
//                    .build();
//
//            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());
//
//
//        }

        @Test
        void removed_from_database_by_super_user() {

        }

        @Test
        void removed_from_database_by_themselves() {

        }

        @Test
        void updated_by_super_user(){

        }

    }

    @SpringBootTest
    class A_Fridge_Member_cannot_be {

        @Autowired
        UserRepository userRepository;

        @Autowired
        FridgeRepository fridgeRepository;


        @Autowired
        FridgeService fridgeService;

        @Autowired
        UserService userService;


        User populateDB() {
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            return user;
        }


        @Test
        public void added_to_database_by_regular_user(){
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFound(1L));



        }

        @Test
        public void added_to_database_by_user_not_in_fridge(){
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFound(1L));



        }

        @Test
        void removed_from_database_by_regular_user() {

        }

        @Test
        void removed_from_database_by_user_not_in_fridge() {

        }

        @Test
        void updated_by_regular_user(){

        }

        @Test
        void updated_by_user_not_in_fridge(){

        }

    }

}