package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.recipe.ItemRecipeScoreMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.ItemRecipeScoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRecipeScoreService {

    private final ItemRepository itemRepository;
    private final RecipeRepository recipeRepository;
    private final ItemRecipeScoreRepository itemRecipeScoreRepository;

    public void generateScores() {
        List<Item> allItems = itemRepository.findAll();
        List<Recipe> allRecipes = recipeRepository.findAll();

        for(Item item : allItems) {
            for(Recipe recipe : allRecipes) {
                generateSingleScore(item, recipe);
            }
        }

    }

    public void testScore(Long itemId, Long recipeId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe Id", recipeId));

        generateSingleScore(item, recipe);
    }

    public void generateScoreForItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        List<Recipe> allRecipes = recipeRepository.findAll();

        for(Recipe recipe : allRecipes) {
            generateSingleScore(item, recipe);
        }
    }

    public void generateScoreForRecipe(Long recipeId) {
        List<Item> allItems = itemRepository.findAll();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe Id", recipeId));

        for(Item item : allItems) {
            generateSingleScore(item, recipe);
        }
    }

    public void generateSingleScore(Item item, Recipe recipe) {
        if(itemRecipeScoreRepository.existsItemRecipeScoreByItem_ItemIdAndRecipe_RecipeId(item.getItemId(), recipe.getRecipeId())) return;

        double score = recipe.getRecipeParts()
                .stream()
                .flatMap(part -> part.getItemsInRecipe().stream())
                .mapToDouble(ingredient -> getScore(item, ingredient))
                .max().orElse(0);

        itemRecipeScoreRepository.save(ItemRecipeScoreMapper.toItemRecipeScore(item, recipe, score));
    }

    private double getScore(Item item, RecipeItems ingredient) {
        if(item.getEan().equals(ingredient.getItem().getEan())) return 1.0;
        else {
            return new JaroWinklerDistance().apply(item.getProductName(), ingredient.getItem().getProductName());
        }
    }

}
