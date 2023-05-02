package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipePartRepository extends JpaRepository<RecipePart, Long>, JpaSpecificationExecutor<RecipePart> {
}
