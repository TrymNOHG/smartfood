package edu.ntnu.idatt2106_2023_06.backend.repo;

import edu.ntnu.idatt2106_2023_06.backend.model.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**

 This repository provides CRUD operations for the ShoppingItem entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface ShoppingItemsRepository extends JpaRepository<ShoppingItems, FridgeItemsId>, JpaSpecificationExecutor<ShoppingItems> {

}