package edu.ntnu.idatt2106_2023_06.backend.repo.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the User entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Modifying
    @Transactional
    @Query(value = "CREATE TRIGGER delete_fridge_member BEFORE DELETE ON users FOR EACH ROW " +
            "BEGIN " +
            "DELETE FROM fridge_members WHERE fridge_members.user_id = OLD.user_id; " +
            "END; ", nativeQuery = true)
    @Profile("!test")
    void createTriggerForDeletingMember();



    @Modifying
    @Transactional
    @Query(value = "DROP TRIGGER IF EXISTS delete_fridge_member", nativeQuery = true)
    @Profile("!test")
    void dropMemberTrigger();

    @Modifying
    @Transactional
    @Query(value = "CREATE TRIGGER delete_user_stat BEFORE DELETE ON users FOR EACH ROW " +
            "BEGIN " +
            "UPDATE stats SET user_id = null WHERE user_id = OLD.user_id; " +
            "END; ", nativeQuery = true)
    @Profile("!test")
    void createTriggerForNullingUserStat();

    @Modifying
    @Transactional
    @Query(value = "DROP TRIGGER IF EXISTS delete_user_stat", nativeQuery = true)
    @Profile("!test")
    void dropUserStatTrigger();

    /**
     * Retrieves an Optional User instance based on the provided username.
     * @param username the username to search for
     * @return an Optional User instance
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves an Optional User instance based on the provided email.
     * @param email   The email to search for
     * @return An Optional User instance
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves an Optional User instance based on the provided user id.
     * @param userId   The user ID to search for
     * @return         An Optional User instance
     */
    Optional<User> findUserByUserId(Long userId);


    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE %:inputString%")
    List<User> findByUsernameContaining(@Param("inputString") String inputString);

    /**
     * This method checks whether a user exists, given the user id.
     * @param userId    ID of the user, given as a Long object.
     * @return          Status of whether user exists.
     */
    boolean existsByUserIdIs(Long userId);

}