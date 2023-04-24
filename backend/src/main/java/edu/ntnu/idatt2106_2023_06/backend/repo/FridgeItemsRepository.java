package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the FridgeItem entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface FridgeItemsRepository extends JpaRepository<FridgeItems, FridgeItemsId>, JpaSpecificationExecutor<FridgeItems> {
    /**
     * Find a FridgeItem by items and fridge
     *
     * @param item The items of product
     * @param fridge the fridge to search in
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<FridgeItems> findByItemAndFridge(Item item, Fridge fridge);

    /**
     * Find FridgeItems by fridge
     *
     * @param fridge the fridge to search in
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<List<FridgeItems>> findByFridge(Fridge fridge);
}
