package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRecipeScoreRepository extends JpaRepository<ItemRecipeScore, ItemRecipeScoreId>, JpaSpecificationExecutor<ItemRecipeScore> {

    /**
     * This method determines whether a given ItemRecipeScore is made, by checking the item id and recipe id.
     * @param itemId        ID of item to be checked, given as a Long object.
     * @param recipeId      ID of recipe to be checked, given as a Long object.
     * @return              Status whether the item recipe score exists or not.
     */
    boolean existsItemRecipeScoreByItem_ItemIdAndRecipe_RecipeId(Long itemId, Long recipeId);

}
