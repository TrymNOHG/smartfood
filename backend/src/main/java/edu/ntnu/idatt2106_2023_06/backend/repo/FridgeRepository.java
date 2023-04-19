package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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
}