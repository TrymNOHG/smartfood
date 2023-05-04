package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.recipe.ItemRecipeScoreMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.ItemRecipeScoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRecipeScoreService {

    private final ItemRepository itemRepository;
    private final RecipeRepository recipeRepository;
    private final FridgeRepository fridgeRepository;
    private final ItemRecipeScoreRepository itemRecipeScoreRepository;
    private final FridgeItemsRepository fridgeItemsRepository;

    @Async
    public void generateScores() {
        List<Item> allItems = itemRepository.findAll();
        List<Recipe> allRecipes = recipeRepository.findAll();

        List<CompletableFuture<ItemRecipeScore>> futures = new ArrayList<>();

        for(Item item : allItems) {
            for(Recipe recipe : allRecipes) {
                futures.add(generateSingleScore(item, recipe));
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    public ItemRecipeScore generateScoreByIds(Long itemId, Long recipeId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe Id", recipeId));

        return generateSingleScore(item, recipe).join();
    }

    @Async
    @Transactional
    public CompletableFuture<List<ItemRecipeScore>> generateScoreForItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        List<Recipe> allRecipes = recipeRepository.findAll();
        List<CompletableFuture<ItemRecipeScore>> futures = new ArrayList<>();

        for(Recipe recipe : allRecipes) {
            futures.add(generateSingleScore(item, recipe));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    public void generateScoreForRecipe(Long recipeId) {
        List<Item> allItems = itemRepository.findAll();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe Id", recipeId));

        for(Item item : allItems) {
            generateSingleScore(item, recipe);
        }
    }

    @Async
    @Transactional
    public CompletableFuture<ItemRecipeScore> generateSingleScore(Item item, Recipe recipe) {
        if(itemRecipeScoreRepository.existsItemRecipeScoreByItem_ItemIdAndRecipe_RecipeId(item.getItemId(), recipe.getRecipeId())) return CompletableFuture.completedFuture(null);

        double score = recipe.getRecipeParts()
                .stream()
                .flatMap(part -> part.getItemsInRecipe().stream())
                .mapToDouble(ingredient -> getScore(item, ingredient))
                .max().orElse(0);

        ItemRecipeScore itemRecipeScore = itemRecipeScoreRepository.save(ItemRecipeScoreMapper.toItemRecipeScore(item, recipe, score));

        return CompletableFuture.completedFuture(itemRecipeScore);
    }

    private double getScore(Item item, RecipeItems ingredient) {
        if(item.getEan() != null && item.getEan().equals(ingredient.getItem().getEan())) return 1.0;
        else {
            String regex = "\\b\\d+\\s*g\\b|\\b\\d+%\\b|\\b[A-Za-z]{2,}\\b";
            String coreProductName = item.getProductName().replaceAll(regex, "").trim();
            String coreIngredientName = ingredient.getItem().getProductName().replaceAll(regex, "").trim();

            double nameSimilarity = new JaroWinklerDistance()
                    .apply(coreProductName.toLowerCase(), coreIngredientName.toLowerCase());

            double descSimilarity = 0;
            if (item.getDesc() != null && ingredient.getItem().getDesc() != null) {
                descSimilarity = new JaroWinklerDistance()
                        .apply(item.getDesc().toLowerCase(), ingredient.getItem().getDesc().toLowerCase());
            }

            double score = ((nameSimilarity + 0.5 * descSimilarity) / 1.5);
            return Math.pow(score, 1.5);
        }
    }

    public Page<Recipe> getRankedRecipeByDate(Long fridgeId, int pageNumber, int pageSize, LocalDateTime date) {
        Fridge fridge = fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge)
                .orElseThrow(() -> new FridgeNotFoundException(fridgeId));

        if(fridgeItems.isEmpty()) {
            return null;
        }

        Map<Long, Double> recipeScores = new HashMap<>();
        for (FridgeItems fridgeItem : fridgeItems) {

            List<ItemRecipeScore> itemRecipeScores = itemRecipeScoreRepository
                    .findByItem_ItemId(fridgeItem.getItem().getItemId())
                    .orElseGet(() -> generateScoreForItem(fridgeItem.getItem().getItemId()).join());

            for (ItemRecipeScore itemRecipeScore : itemRecipeScores) {
                double weightedScore = getWeightedScore(itemRecipeScore, fridgeItem, date);
                recipeScores.merge(itemRecipeScore.getRecipe().getRecipeId(), weightedScore, Double::sum);
            }
        }


        List<Long> recipeIds = new ArrayList<>(recipeScores.keySet());

        recipeIds.sort((r1, r2) -> Double.compare(recipeScores.get(r2), recipeScores.get(r1)));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Recipe> recipes = recipeRepository.findAllById(recipeIds.subList((int) pageable.getOffset(), (int) (pageable.getOffset() + pageable.getPageSize())));

        return new PageImpl<>(recipes, pageable, recipeIds.size());
    }

    public Page<Recipe> getRankedRecipesByFridge(Long fridgeId, int pageNumber, int pageSize) {
        return getRankedRecipeByDate(fridgeId, pageNumber, pageSize, LocalDateTime.now());
    }



    public double getWeightedScore(ItemRecipeScore itemRecipeScore, FridgeItems fridgeItem, LocalDateTime date) {
        long daysLeft = ChronoUnit.DAYS.between(date, fridgeItem.getExpirationDate());
        double weight = 1 / Math.sqrt(daysLeft);

        return weight * itemRecipeScore.getScore();
    }


}
