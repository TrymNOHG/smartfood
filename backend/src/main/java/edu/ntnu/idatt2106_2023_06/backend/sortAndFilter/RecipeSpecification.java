package edu.ntnu.idatt2106_2023_06.backend.sortAndFilter;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecification {

//    public static Specification<Recipe> recipeWithFridgeItems(Long fridgeId) {
//        Specification<Recipe> specWithFridgeItems = recipeHasFridgeItems(fridgeId);
//        Specification<Recipe> specWithSimilarItems = recipeHasSimilarItems(fridgeId);
//
//        return Specification.where(specWithFridgeItems).and(specWithSimilarItems);
//    }

    public static Specification<Recipe> recipeHasFridgeItems(Long fridgeId) {
        return (root, query, cb) -> {
            Subquery<Long> fridgeItemSubquery = query.subquery(Long.class);
            Root<FridgeItems> fridgeItemRoot = fridgeItemSubquery.from(FridgeItems.class);
            fridgeItemSubquery.select(fridgeItemRoot.get("item").get("itemId"));
            fridgeItemSubquery.where(cb.equal(fridgeItemRoot.get("fridge").get("id"), fridgeId));

            Join<Recipe, RecipePart> recipePartJoin = root.join("recipeParts");
            Join<RecipePart, RecipeItems> itemInRecipeJoin = recipePartJoin.join("itemsInRecipe");

            query.groupBy(root.get("recipeId")); // group by recipe ID

            // count the number of matching items and order by it
            query.orderBy(cb.desc(cb.sum(cb.<Long>selectCase()
                    .when(itemInRecipeJoin.get("item").get("itemId").in(fridgeItemSubquery), 1L)
                    .otherwise(0L)
            )));

            return (Predicate) cb.in(itemInRecipeJoin.get("item").get("itemId")).value(fridgeItemSubquery);
        };
    }


}
