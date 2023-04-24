package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the ShoppingItem entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface ShoppingItemsRepository extends JpaRepository<ShoppingItems, FridgeItemsId>, JpaSpecificationExecutor<ShoppingItems> {
    /**
     * Find a ShoppingItem by items, fridge and suggestion
     *
     * @param item The items of product
     * @param fridge the fridge to search in
     * @param suggestion is it a suggestion
     * @return An Optional containing the ShoppingItem, or an empty Optional if not found
     */
    Optional<ShoppingItems> findByItemAndFridgeAndSuggestion(Item item, Fridge fridge, boolean suggestion);

    /**
     * Find ShoppingItems by fridge
     *
     * @param fridge the fridge to search in
     * @return An Optional containing the ShoppingItem, or an empty Optional if not found
     */
    Optional<List<ShoppingItems>> findByFridge(Fridge fridge);
}
