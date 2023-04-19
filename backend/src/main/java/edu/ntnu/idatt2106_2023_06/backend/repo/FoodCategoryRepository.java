package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the FoodCategory entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    /**
     * Find a Category by its name
     *
     * @param name The Category name of the Category to find
     * @return An Optional containing the Category found, or an empty Optional if not found
     */
    Optional<FoodCategory> findByCategoryName(String name);

    /**
     * Find a Category by its ID
     *
     * @param categoryId The ID of the Category to find
     * @return An Optional containing the Category found, or an empty Optional if not found
     */
    Optional<FoodCategory> findCategoryByCategoryId(Long categoryId);

}

