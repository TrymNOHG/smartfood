package edu.ntnu.idatt2106_2023_06.backend.repo.users;

import edu.ntnu.idatt2106_2023_06.backend.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    void createTrigger();

    @Modifying
    @Transactional
    @Query(value = "DROP TRIGGER IF EXISTS delete_fridge_member", nativeQuery = true)
    @Profile("!test")
    void dropTrigger();

    /**
     Retrieves an Optional User instance based on the provided username.
     @param username the username to search for
     @return an Optional User instance
     */
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}