package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.RecipeItemId;
import edu.ntnu.idatt2106_2023_06.backend.model.RecipeItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**

 This repository provides CRUD operations for the RecipeItem entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface RecipeItemsRepository extends JpaRepository<RecipeItems, RecipeItemId>, JpaSpecificationExecutor<RecipeItems> {

}
