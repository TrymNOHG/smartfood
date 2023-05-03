package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 This repository provides CRUD operations for the Recipe entity.

 It extends JpaRepository and JpaSpecificationExecutor interfaces.

 JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 search functionality using specifications.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    /**
     * This method retrieves a recipe by its name.
     * @param recipeName    Name of the recipe, given as a String.
     * @return              An optional of the recipe.
     */
    Optional<Recipe> findRecipeByRecipeNameContainingIgnoreCase(String recipeName);

    /**
     * This method retrieves a page of recipes by recipe name.
     * @param recipeName Name of recipe, given as a String
     * @param pageable   The paging information, given as a Pageable object.
     * @return           An optional containing the page of recipes.
     */
    Optional<Page<Recipe>> findByRecipeNameContainingIgnoreCase(String recipeName, Pageable pageable);

    /**
     * This method retrieves a recipe by recipe id.
     * @param recipeId   Id of the recipe.
     * @return           An optional containing the recipe.
     */
    Optional<Recipe> findByRecipeId(Long recipeId);



    /**
     * This method retrieves a random list of recipe objects given paging details.
     * @param pageable  Paging details, given as a Pageable object.
     * @return          A list of random recipes.
     */
    @Query(value = "SELECT r FROM Recipe r ORDER BY RAND()")
    List<Recipe> findRandomSubset(Pageable pageable);

}