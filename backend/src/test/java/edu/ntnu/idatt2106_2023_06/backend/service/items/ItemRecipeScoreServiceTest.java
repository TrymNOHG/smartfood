package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.ItemRecipeScoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ItemRecipeScoreServiceTest {


    @Nested
    @SpringBootTest
    class GenerateScores{

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Test
        @Transactional
        void doesNotThrowException(){
            assertDoesNotThrow(() ->{
                itemRecipeScoreService.generateScores();
            });
        }
    }

    @Nested
    @SpringBootTest
    class GenerateScoreByIds{

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Test
        @Transactional
        void returns_ItemRecipeScore(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Recipe recipe = Recipe.builder()
                    .recipeId(1L)
                    .recipeName("TestName")
                    .description("TestDescription")
                    .author("TestAuthor")
                    .servingSize(1)
                    .difficulty(1)
                    .thumbnailLink("TestLink")
                    .cookTime(20)
                    .recipeParts(new ArrayList<>())
                    .instructions(new ArrayList<>())
                    .recipeAllergenSet(new HashSet<>())
                    .build();
            itemRepository.save(item);
            recipeRepository.save(recipe);

            assertEquals(ItemRecipeScore.class, itemRecipeScoreService.generateScoreByIds(1L, 1L).getClass());
        }

        @Test
        @Transactional
        void returns_ItemNotFoundException(){
            Recipe recipe = Recipe.builder()
                    .recipeId(1L)
                    .recipeName("TestName")
                    .description("TestDescription")
                    .author("TestAuthor")
                    .servingSize(1)
                    .difficulty(1)
                    .thumbnailLink("TestLink")
                    .cookTime(20)
                    .recipeParts(new ArrayList<>())
                    .instructions(new ArrayList<>())
                    .recipeAllergenSet(new HashSet<>())
                    .build();
            recipeRepository.save(recipe);

            assertThrows(ItemNotFoundException.class, () -> {
                itemRecipeScoreService.generateScoreByIds(1L, 1L);
            });
        }

        @Test
        @Transactional
        void returns_RecipeNotFoundException(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);


            assertThrows(RecipeNotFoundException.class, () -> {
                itemRecipeScoreService.generateScoreByIds(1L, 1L);
            });
        }
    }

    @Nested
    @SpringBootTest
    class GenerateScoreForItem {

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);


            assertDoesNotThrow(() -> {itemRecipeScoreService.generateScoreForItem(1L);});
        }
    }
    @Nested
    @SpringBootTest
    class GenerateScoreForRecipe {

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;


        @Test
        @Transactional
        void does_not_throw_exception(){

            Recipe recipe = Recipe.builder()
                    .recipeId(1L)
                    .recipeName("TestName")
                    .description("TestDescription")
                    .author("TestAuthor")
                    .servingSize(1)
                    .difficulty(1)
                    .thumbnailLink("TestLink")
                    .cookTime(20)
                    .recipeParts(new ArrayList<>())
                    .instructions(new ArrayList<>())
                    .recipeAllergenSet(new HashSet<>())
                    .build();
            recipeRepository.save(recipe);


            assertDoesNotThrow(() -> itemRecipeScoreService.generateScoreForRecipe(1L));
        }

        @Test
        @Transactional
        void returns_RecipeNotFoundException(){


            assertThrows(RecipeNotFoundException.class, () -> {
                itemRecipeScoreService.generateScoreForRecipe(1L);
            });
        }
    }

    @Nested
    @SpringBootTest
    class GenerateSingleScore {

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        ItemRecipeScoreRepository itemRecipeScoreRepository;

        @Test
        @Transactional
        void does_not_throw_exception(){

            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Recipe recipe = Recipe.builder()
                    .recipeId(1L)
                    .recipeName("TestName")
                    .description("TestDescription")
                    .author("TestAuthor")
                    .servingSize(1)
                    .difficulty(1)
                    .thumbnailLink("TestLink")
                    .cookTime(20)
                    .recipeParts(new ArrayList<>())
                    .instructions(new ArrayList<>())
                    .recipeAllergenSet(new HashSet<>())
                    .build();


            assertDoesNotThrow(() -> itemRecipeScoreService.generateSingleScore(item ,recipe));
        }
    }

    @Nested
    @SpringBootTest
    class GetScore {
        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Item item = new Item(1L, "Tine Melk 1L stor kartong", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Item item2 = new Item(1L, "Tine Melk 1L stor kartong", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "23456789", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItem = new RecipeItems(null, item2, new RecipePart(), 1, "l");


            assertDoesNotThrow(() -> itemRecipeScoreService.getScore(item, recipeItem));
        }
        @Test
        @Transactional
        void returns_1_if_same_item(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4,  new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItem = new RecipeItems(null, item, new RecipePart(), 1, "L");


            assertEquals(1, itemRecipeScoreService.getScore(item, recipeItem));
        }

    }

    @Nested
    @SpringBootTest
    class GetRankedRecipeByDate {

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        ItemRecipeScoreRepository itemRecipeScoreRepository;

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;



        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 500.0, "l", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(item.getItemId(), fridge.getFridgeId());
            FridgeItems items = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(items);
            Recipe recipe = Recipe.builder()
                    .recipeId(1L)
                    .recipeName("TestName")
                    .description("TestDescription")
                    .author("TestAuthor")
                    .servingSize(1)
                    .difficulty(1)
                    .thumbnailLink("TestLink")
                    .cookTime(20)
                    .recipeParts(new ArrayList<>())
                    .instructions(new ArrayList<>())
                    .recipeAllergenSet(new HashSet<>())
                    .build();
            recipeRepository.save(recipe);
            ItemRecipeScoreId itemRecipeScoreId = new ItemRecipeScoreId(item.getItemId(), recipe.getRecipeId());
            ItemRecipeScore itemRecipeScore = new ItemRecipeScore(itemRecipeScoreId, item, recipe, 0.9);
            itemRecipeScoreRepository.save(itemRecipeScore);

            assertDoesNotThrow(() -> itemRecipeScoreService.getRankedRecipeByDate(1L, 0, 1, LocalDateTime.now()));
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            assertThrows(FridgeNotFoundException.class, () -> itemRecipeScoreService.getRankedRecipeByDate(1L, 1, 10, LocalDateTime.now()));
        }
    }

    @Nested
    @SpringBootTest
    class GetRankedRecipesByFridge {

        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        ItemRecipeScoreRepository itemRecipeScoreRepository;

        @Autowired
        FridgeRepository fridgeRepository;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            fridgeRepository.save(fridge);


            assertDoesNotThrow(() -> itemRecipeScoreService.getRankedRecipesByFridge(1L, 1, 10));
        }
    }

    @Nested
    @SpringBootTest
    class GetWeightedScore {
        @Autowired
        ItemRecipeScoreService itemRecipeScoreService;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        ItemRecipeScoreRepository itemRecipeScoreRepository;

        @Autowired
        FridgeRepository fridgeRepository;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Item item = new Item();
            item.setItemId(1L);

            Recipe recipe = new Recipe();
            recipe.setRecipeId(2L);


            ItemRecipeScoreId itemRecipeScoreId = new ItemRecipeScoreId(item.getItemId(), recipe.getRecipeId());
            ItemRecipeScore itemRecipeScore = ItemRecipeScore.builder()
                    .itemRecipeScoreId(itemRecipeScoreId)
                    .item(item)
                    .recipe(recipe)
                    .score(0.8)
                    .build();

            Item item1 = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 500.0, "l", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems items = FridgeItems
                    .builder()
                    .fridge(new Fridge())
                    .item(item1)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            assertDoesNotThrow(() -> itemRecipeScoreService.getWeightedScore(itemRecipeScore, items, LocalDateTime.now()));
        }
    }


    }
