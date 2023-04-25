package edu.ntnu.idatt2106_2023_06.backend.repo.item;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the Item entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    /**
     * Finds an item by its items ID.
     *
     * @param itemId the ID of the item to find
     * @return an Optional containing the item with the given ID, or an empty Optional if no such item exists
     */
    Optional<Item> findByItemId(Long itemId);

    /**
     * Finds an item by its item name and store.
     *
     * @param name the name of the item to find
     * @param store the store to use
     * @return an Optional containing the item with the given name and store, or an empty Optional if no such item exists
     */
    Optional<Item> findByProductNameAndStore(String name, Store store);

    /**
     * Finds a list of items by their store.
     *
     * @param store the store of the items to find
     * @return an Optional containing the list of items with the given main category, or an empty Optional if no such items exist
     */
    Optional<List<Item>> findItemsByStore(Store store);

    /**
     * Finds a list of items whose brief description contains the given phrase, ignoring case.
     *
     * @param phrase the phrase to search for
     * @return an Optional containing the list of items whose brief description contains the given phrase, or an empty Optional if no such items exist
     */
    Optional<List<Item>> findItemsByDescContainingIgnoreCase(String phrase);

    /**
     * Finds an items by their product name.
     *
     * @param productName the product name to search for
     * @return an Optional containing the items with the given product name, or an empty Optional if no such items exist
     */
    Optional<Item> findItemByProductName(String productName);


    /**
     * Finds a list of items whose price is less than the given price.
     *
     * @param price the maximum price of the items to find
     * @return an Optional containing the list of items whose price is less than the given price, or an empty Optional if no such items exist
     */
    Optional<List<Item>> findItemsByPriceLessThan(double price);

    /**
     * Finds items with prices less than or equal to the given price.
     *
     * @param price The maximum price to search for.
     * @return An optional list of items with prices less than or equal to the given price.
     */
    Optional<List<Item>> findItemsByPriceLessThanEqual(double price);

    /**
     * Finds items with prices greater than the given price.
     *
     * @param price The minimum price to search for.
     * @return An optional list of items with prices greater than the given price.
     */
    Optional<List<Item>> findItemsByPriceGreaterThan(double price);

    /**
     * Finds items with prices greater than or equal to the given price.
     *
     * @param price The minimum price to search for.
     * @return An optional list of items with prices greater than or equal to the given price.
     */
    Optional<List<Item>> findItemsByPriceGreaterThanEqual(double price);

    /**
     * Finds items with prices between the given lower and upper bounds (inclusive).
     *
     * @param lowerBound The lower bound of the price range.
     * @param upperBound The upper bound of the price range.
     * @return An optional list of items with prices between the given lower and upper bounds.
     */
    Optional<List<Item>> findItemsByPriceBetween(double lowerBound, double upperBound);

    /**
     * Finds items with purchase dates before a given date.
     *
     * @param purchaseDate Date of purchase.
     * @return An optional list of items with purchase dates before a given items.
     */
    Optional<List<Item>> findItemsByPurchaseDateBefore(Date purchaseDate);





}

