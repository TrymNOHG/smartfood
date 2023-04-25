package edu.ntnu.idatt2106_2023_06.backend.repo.store;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**

 This repository provides CRUD operations for the Store entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * Find a Store by its name
     *
     * @param name The Category name of the Category to find
     * @return An Optional containing the Category found, or an empty Optional if not found
     */
    Optional<Store> findByStoreName(String name);

    /**
     * Find a Store by its ID
     *
     * @param categoryId The ID of the Category to find
     * @return An Optional containing the Category found, or an empty Optional if not found
     */
    Optional<Store> findStoreByStoreId(Long categoryId);

}

