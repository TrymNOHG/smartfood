package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**

 This repository provides CRUD operations for the Allergen entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long>, JpaSpecificationExecutor<Allergen> {

    Optional<Allergen> findAllergenByAllergenName(String name);

}
