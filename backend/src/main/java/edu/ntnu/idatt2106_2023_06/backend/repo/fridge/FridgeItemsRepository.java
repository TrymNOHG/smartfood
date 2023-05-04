package edu.ntnu.idatt2106_2023_06.backend.repo.fridge;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
     * Find a fridgeItem with the given name
     *
     * @param productName The name of the product
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<FridgeItems> findByItem_ProductName(String productName);

    /**
     * Find a FridgeItem by items and fridge
     *
     * @param item The items of product
     * @param fridge the fridge to search in
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<FridgeItems> findByItemAndFridge(Item item, Fridge fridge);

    /**
     * Find a FridgeItem by item ID and fridge ID
     *
     * @param itemId    The items of product, given by a Long object
     * @param fridgeId  The fridge to search in, given by a Long object
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<FridgeItems> findByItem_ItemIdAndFridge_FridgeId(Long itemId, Long fridgeId);

    /**
     * Find FridgeItems by fridge
     *
     * @param fridge the fridge to search in
     * @return An Optional containing the FridgeItem, or an empty Optional if not found
     */
    Optional<List<FridgeItems>> findByFridge(Fridge fridge);

    /**
     * Finds fridge items with purchase dates before a given date.
     *
     * @param purchaseDate Date of purchase.
     * @return An optional list of items with purchase dates before a given items.
     */
    Optional<List<FridgeItems>> findFridgeItemsByPurchaseDateBefore(@NonNull LocalDateTime purchaseDate);

    /**
     * This method retrieves a list of all the fridge items from a given fridge.
     * @param fridgeId  The ID of the fridge, given as a Long
     * @return          An optional containing the list of fridge items.
     */
    Optional<List<FridgeItems>> findAllByFridge_FridgeId(Long fridgeId);
}

