package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
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
import java.util.List;

import static org.junit.Assert.fail;

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
        @Autowired
        private FridgeMemberRepository fridgeMemberRepository;


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


        @Test
        void added_to_database(){
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            Fridge expectedFridge = new Fridge(1L, "Home Fridge",
                    new HashSet<>(), new ArrayList<>(), new ArrayList<>());

            Fridge actualFridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            Assertions.assertEquals(expectedFridge, actualFridge);
        }

        @Test
        void updated_through_name_by_super_user(){
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            String newName = "Away Fridge";

            FridgeDTO fridgeDTO = new FridgeDTO(1L, newName);

            fridgeService.updateFridgeName(fridgeDTO, user.getUsername());

            Fridge actualFridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            Assertions.assertEquals(newName, actualFridge.getFridgeName());

        }

        @Test
        void created_by_user() {
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            Assertions.assertEquals(1, fridgeService.retrieveFridgeIdsByUsername(user.getUsername()).size());

            fridgeService.createFridge("New fridge", user.getUsername());

            List<FridgeDTO> fridgeDTOS = fridgeService.retrieveFridgesByUsername(user.getUsername())
                            .fridgeDTOS();
            Assertions.assertEquals(2, fridgeDTOS.size());
            Assertions.assertEquals("New fridge", fridgeDTOS.get(1).fridgeName());

        }

    }

    @Nested
    @SpringBootTest
    class Fridge_cannot_be{

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

        @Test
        void updated_through_name_by_regular_user(){
            //TODO: fix
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            String newName = "Away Fridge";

            FridgeDTO fridgeDTO = new FridgeDTO(1L, newName);

            fridgeService.updateFridgeName(fridgeDTO, user.getUsername());

            Fridge actualFridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            Assertions.assertEquals(newName, actualFridge.getFridgeName());

        }

        @Test
        void updated_through_name_by_non_member(){
            //TODO: fix
            User user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(user);
            fridgeService.initializeFridge(user.getUsername());

            String newName = "Away Fridge";

            FridgeDTO fridgeDTO = new FridgeDTO(1L, newName);

            fridgeService.updateFridgeName(fridgeDTO, user.getUsername());

            Fridge actualFridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            Assertions.assertEquals(newName, actualFridge.getFridgeName());

        }

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


        @Test
        public void added_to_database_as_super_user_by_super_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());


            //TODO: test that exception is not thrown

            try {

            FridgeMember fridgeMember = fridgeMemberRepository
                    .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                            .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());

            } catch (Exception e) {
                fail(e.getMessage());
            }


        }

        @Test
        public void added_to_database_as_regular_user_by_super_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            try {

                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());

            } catch (Exception e) {
                fail(e.getMessage());
            }

        }

        @Test
        void removed_from_database_by_super_user() {
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());


            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            fridgeService.deleteUserFromFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertFalse(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));
        }

        @Test
        void removed_from_database_by_themselves() {
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());


            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            fridgeService.deleteUserFromFridge(fridgeUserDTO, newUser.getUsername());

            Assertions.assertFalse(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));
        }

        @Test
        void updated_by_super_user_from_user_to_super_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, user.getUsername());

                fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());

            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }

        @Test
        void updated_by_super_user_from_super_user_to_super_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, user.getUsername());

                fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());

            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }

        @Test
        void updated_by_super_user_from_super_user_to_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, user.getUsername());

                fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());

            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }

        @Test
        void updated_by_super_user_from_user_to_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, user.getUsername());

                fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());

            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }

    }

    @Nested
    @SpringBootTest
    @Nested
    class A_Fridge_Member_cannot_be {

        @Autowired
        UserRepository userRepository;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;


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
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));


            User thirdUser = User
                    .builder()
                    .userId(3L)
                    .username("J")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test123@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(thirdUser);

            FridgeUserDTO thirdFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(thirdUser.getUsername())
                    .build();

            try {
                fridgeService.addUserToFridge(thirdFridgeMember, newUser.getUsername());
                Assertions.fail("User was added when it wasn't supposed to");


                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertTrue(fridgeMember.isSuperUser());

            } catch (Exception e) {
                if(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, thirdUser.getUsername())) {
                    Assertions.fail("User was added even though an exception was thrown.");
                }
            }

        }

        @Test
        public void added_to_database_by_user_not_in_fridge(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(newUser);

            Assertions.assertTrue(userRepository.existsById(2L));


            User thirdUser = User
                    .builder()
                    .userId(3L)
                    .username("J")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test123@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(thirdUser);
            Assertions.assertTrue(userRepository.existsById(3L));


            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(thirdUser.getUsername())
                    .build();


            try {
                fridgeService.addUserToFridge(fridgeUserDTO, newUser.getUsername());
                Assertions.fail("User was added when it wasn't supposed to");

            } catch (Exception e) {
                if(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, thirdUser.getUsername())) {
                    Assertions.fail("User was added even though an exception was thrown.");
                }
            }

        }

        @Test
        public void added_to_database_by_non_existent_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));


            User thirdUser = User
                    .builder()
                    .userId(2L)
                    .username("J")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test123@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();


            userRepository.save(thirdUser);
            Assertions.assertTrue(userRepository.existsById(2L));


            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username("Hans")
                    .build();


            try {
                fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());
                Assertions.fail("User was added when it wasn't supposed to");

            } catch (Exception e) {
                if(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, thirdUser.getUsername())) {
                    Assertions.fail("User was added even though an exception was thrown.");
                }
            }

        }

        @Test
        void removed_from_database_by_regular_user() {
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());

                fridgeUserDTO = FridgeUserDTO
                        .builder()
                        .fridgeId(1L)
                        .isSuperUser(true)
                        .username(user.getUsername())
                        .build();
                fridgeService.deleteUserFromFridge(fridgeUserDTO, newUser.getUsername());
                fail("Regular User was able to delete another user.");
            } catch (Exception e) {
                Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, user.getUsername()));
            }
        }

        @Test
        void removed_from_database_by_user_not_in_fridge() {
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());
            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, "Hans1234"));

            User thirdUser = User
                    .builder()
                    .userId(3L)
                    .username("Nils1234")
                    .password("password")
                    .firstName("Nils")
                    .lastName("Norman")
                    .email("test123@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(thirdUser);
            Assertions.assertTrue(userRepository.existsById(3L));

            try {
                fridgeService.deleteUserFromFridge(fridgeUserDTO, thirdUser.getUsername());
                fail("User not part of fridge should not be able to add user to fridge");
            } catch (Exception e) {
                Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));
            }
        }

        @Test
        void updated_by_regular_user(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            User thirdUser = User
                    .builder()
                    .userId(3L)
                    .username("Hanne1234")
                    .password("password")
                    .firstName("Hanne")
                    .lastName("Norman")
                    .email("test321@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(thirdUser);

            fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(thirdUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, thirdUser.getUsername()));


            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, thirdUser.getUsername());

                fail("Update was successful...");

            } catch (Exception e) {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
            }
        }

        @Test
        void updated_by_user_not_in_fridge(){
            //This user is automatically superuser, since they started the fridge
            User user = populateDB();
            Fridge fridge = fridgeRepository.findByFridgeId(1L)
                    .orElseThrow(() -> new FridgeNotFoundException(1L));

            User newUser = User
                    .builder()
                    .userId(2L)
                    .username("Hans1234")
                    .password("password")
                    .firstName("Hans")
                    .lastName("Norman")
                    .email("test@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(newUser);

            FridgeUserDTO fridgeUserDTO = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(false)
                    .username(newUser.getUsername())
                    .build();

            fridgeService.addUserToFridge(fridgeUserDTO, user.getUsername());

            Assertions.assertTrue(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername()));

            User thirdUser = User
                    .builder()
                    .userId(3L)
                    .username("Hanne1234")
                    .password("password")
                    .firstName("Hanne")
                    .lastName("Norman")
                    .email("test321@hotmail.com")
                    .memberships(new HashSet<>())
                    .build();

            userRepository.save(thirdUser);

            Assertions.assertFalse(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(1L, thirdUser.getUsername()));


            FridgeUserDTO updatedFridgeMember = FridgeUserDTO
                    .builder()
                    .fridgeId(1L)
                    .isSuperUser(true)
                    .username(newUser.getUsername())
                    .build();

            try {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
                fridgeService.updateUserFromFridge(updatedFridgeMember, thirdUser.getUsername());

                fail("Update was successful...");

            } catch (Exception e) {
                FridgeMember fridgeMember = fridgeMemberRepository
                        .findFridgeMemberByFridge_FridgeIdAndUser_Username(1L, newUser.getUsername())
                        .orElseThrow(() -> new UserNotFoundException(newUser.getUsername()));

                Assertions.assertFalse(fridgeMember.isSuperUser());
            }
        }

    }

}