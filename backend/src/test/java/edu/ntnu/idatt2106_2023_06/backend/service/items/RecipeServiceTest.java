package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionAddDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionLoad;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeSuggestionNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestion;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestionId;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ShoppingItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeSuggestionRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class RecipeServiceTest {

    @Nested
    @SpringBootTest
    class GetRecipeItems{

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        RecipeService recipeService;

        @Test
        @Transactional
        void gets_correct_Recipe(){
            Recipe recipe = Recipe.builder()
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
            RecipeLoadDTO recipeLoadDTO = recipeService.getRecipe("TestName");

            assertEquals("TestName", recipeLoadDTO.getRecipeName());
        }

        @Test
        @Transactional
        void throws_RecipeNotFoundFoundException(){

            assertThrows(RecipeNotFoundException.class, () -> {
                RecipeLoadDTO recipeLoadDTO = recipeService.getRecipe("TestName");
            });
        }

    }

    @Nested
    @SpringBootTest
    class AddRecipeSuggestion{


        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        RecipeSuggestionRepository recipeSuggestionRepository;

        @Autowired
        RecipeService recipeService;

        @Test
        @Transactional
        void adds_correct_Recipe(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
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

            RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1L, 1L);
            recipeService.addRecipeSuggestion(recipeSuggestionAddDTO);

            assertDoesNotThrow(() -> {
                recipeSuggestionRepository.findAllByFridge(fridge);

            });
        }


        @Test
        @Transactional
        void throws_RecipeNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);

            RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1L, 1L);

            assertThrows(RecipeNotFoundException.class, () -> {
                recipeService.addRecipeSuggestion(recipeSuggestionAddDTO);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            Recipe recipe = Recipe.builder()
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
            User user = User
                    .builder()
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);

            RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1L, 1L);

            assertThrows(FridgeNotFoundException.class, () -> {
                recipeService.addRecipeSuggestion(recipeSuggestionAddDTO);

            });
        }

        @Test
        @Transactional
        void throws_UserNotFoundException(){
            Recipe recipe = Recipe.builder()
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
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1L, 1L);

            assertThrows(UserNotFoundException.class, () -> {
                recipeService.addRecipeSuggestion(recipeSuggestionAddDTO);

            });
        }

    }

    @Nested
    @SpringBootTest
    class LoadRecipeSuggestion{

        @Autowired
        FridgeRepository fridgeRepository;


        @Autowired
        UserRepository userRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        RecipeSuggestionRepository recipeSuggestionRepository;

        @Autowired
        RecipeService recipeService;

        @Test
        @Transactional
        void gets_correct_Recipe(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
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

            RecipeSuggestion recipeSuggestion = RecipeSuggestion.builder()
                    .id(new RecipeSuggestionId(recipe.getRecipeId(), fridge.getFridgeId(), user.getUserId()))
                    .recipe(recipe)
                    .fridge(fridge)
                    .user(user)
                    .build();

            recipeSuggestionRepository.save(recipeSuggestion);

            List<RecipeSuggestionLoad> recipeSuggestionLoadList = recipeService.loadRecipeSuggestion(fridge.getFridgeId());

            assertEquals(1L, recipeSuggestionLoadList.get(0).UserId());

        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            assertThrows(FridgeNotFoundException.class, () -> {
                recipeService.loadRecipeSuggestion(1L);

            });
        }

        @Test
        @Transactional
        void is_empty(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            assertTrue(recipeService.loadRecipeSuggestion(1L).isEmpty());
        }


    }

    @Nested
    @SpringBootTest
    class DeleteRecipeSuggestion{
        @Autowired
        FridgeRepository fridgeRepository;


        @Autowired
        UserRepository userRepository;

        @Autowired
        RecipeRepository recipeRepository;

        @Autowired
        RecipeSuggestionRepository recipeSuggestionRepository;

        @Autowired
        RecipeService recipeService;

        @Test
        @Transactional
        void removes_correct_recipe_suggestion(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
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

            RecipeSuggestion recipeSuggestion = RecipeSuggestion.builder()
                    .id(new RecipeSuggestionId(recipe.getRecipeId(), fridge.getFridgeId(), user.getUserId()))
                    .recipe(recipe)
                    .fridge(fridge)
                    .user(user)
                    .build();

            recipeSuggestionRepository.save(recipeSuggestion);

            recipeService.deleteRecipeSuggestion(recipe.getRecipeId(), fridge.getFridgeId(), user.getUserId());
            assertTrue(recipeSuggestionRepository.findAllByFridge(fridge).orElseThrow().isEmpty());
        }

        @Test
        @Transactional
        void throws_RecipeNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);


            assertThrows(RecipeNotFoundException.class, () -> {
                recipeService.deleteRecipeSuggestion(1L, fridge.getFridgeId(), user.getUserId());

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            Recipe recipe = Recipe.builder()
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
            User user = User
                    .builder()
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);


            assertThrows(FridgeNotFoundException.class, () -> {
                recipeService.deleteRecipeSuggestion(recipe.getRecipeId(), 1L, user.getUserId());

            });
        }

        @Test
        @Transactional
        void throws_UserNotFoundException(){
            Recipe recipe = Recipe.builder()
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
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);


            assertThrows(UserNotFoundException.class, () -> {
                recipeService.deleteRecipeSuggestion(recipe.getRecipeId(), fridge.getFridgeId(), 1L);

            });
        }
    }
}
