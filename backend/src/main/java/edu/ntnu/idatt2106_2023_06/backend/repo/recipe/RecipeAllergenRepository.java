package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergen;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergenId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeAllergenRepository extends JpaRepository<RecipeAllergen, RecipeAllergenId>, JpaSpecificationExecutor<RecipeAllergen> {
}
