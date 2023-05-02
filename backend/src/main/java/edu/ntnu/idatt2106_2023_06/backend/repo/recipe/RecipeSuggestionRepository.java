package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;


import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestion;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the RecipeSuggestion entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface RecipeSuggestionRepository extends JpaRepository<RecipeSuggestion, RecipeSuggestionId>, JpaSpecificationExecutor<RecipeSuggestion> {
    Optional<List<RecipeSuggestion>> findAllByFridge(Fridge fridge);
}
