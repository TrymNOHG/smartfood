package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**

 This repository provides CRUD operations for the Fridge entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long>, JpaSpecificationExecutor<Fridge> {
    /**
     * Finds a fridge by its fridge ID.
     *
     * @param fridgeId the ID of the fridge to find
     * @return an Optional containing the fridge with the given ID, or an empty Optional if no such fridge exists
     */
    Optional<Fridge> findByFridgeId(Long fridgeId);

    @Modifying
    @Transactional
    @Query(value = "CREATE TRIGGER fridge_member_deleted AFTER DELETE ON fridge_members FOR EACH ROW " +
            "BEGIN " +
            "IF OLD.super_user = 1 THEN " +
            "IF NOT EXISTS(SELECT 1 FROM fridge_members WHERE fridge_id = OLD.fridge_id AND super_user = 1) THEN " +
            "DELETE FROM fridge WHERE fridge.fridge_id = OLD.fridge_id; " +
            "END IF; " +
            "END IF; " +
            "END; ", nativeQuery = true)
    @Profile("!test")
    void createTriggerForDeletingMember();

    @Modifying
    @Transactional
    @Query(value = "DROP TRIGGER IF EXISTS fridge_member_deleted", nativeQuery = true)
    @Profile("!test")
    void dropMemberTrigger();

    @Modifying
    @Transactional
    @Query(value = "CREATE TRIGGER fridge_deleted " +
            "BEFORE DELETE ON fridge " +
            "FOR EACH ROW " +
            "BEGIN " +
            "UPDATE stats SET fridge_id = NULL WHERE fridge_id = OLD.fridge_id; " +
            "END;", nativeQuery = true)
    @Profile("!test")
    void createTriggerForNullingFridgeStats();

    @Modifying
    @Transactional
    @Query(value = "DROP TRIGGER IF EXISTS fridge_deleted", nativeQuery = true)
    @Profile("!test")
    void dropStatsTrigger();


}