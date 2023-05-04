package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.RecipeSuggestionNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.recipe.RecipeMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final ItemRecipeScoreService itemRecipeScoreService;
    private final FridgeItemsRepository fridgeItemsRepository;
    private final FridgeRepository fridgeRepository;
    private final UserRepository userRepository;
    private final RecipeSuggestionRepository recipeSuggestionRepository;

    public RecipeLoadDTO getRecipe(String recipeName) {
        Recipe recipe = recipeRepository.findRecipeByRecipeNameContainingIgnoreCase(recipeName)
                .orElseThrow(() -> new RecipeNotFoundException("Name of the recipe" , recipeName));
        logger.info("Recipe found. Creating DTO");
        return RecipeMapper.toRecipeLoadDTO(recipe);
    }

    public void addRecipeSuggestion(RecipeSuggestionAddDTO recipeSuggestionAddDTO){
        Recipe recipe = recipeRepository.findByRecipeId(recipeSuggestionAddDTO.recipeId()).orElseThrow(() -> new RecipeNotFoundException("Name of the recipe" , recipeSuggestionAddDTO.recipeId()));
        Fridge fridge = fridgeRepository.findByFridgeId(recipeSuggestionAddDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(recipeSuggestionAddDTO.fridgeId()));
        User user = userRepository.findUserByUserId(recipeSuggestionAddDTO.userId()).orElseThrow(() -> new UserNotFoundException(recipeSuggestionAddDTO.userId()));
        RecipeSuggestion recipeSuggestion = RecipeSuggestion.builder()
                .id(new RecipeSuggestionId(recipeSuggestionAddDTO.recipeId(), recipeSuggestionAddDTO.fridgeId(), recipeSuggestionAddDTO.userId()))
                .recipe(recipe)
                .fridge(fridge)
                .user(user)
                .build();
        recipeSuggestionRepository.save(recipeSuggestion);
    }

    public List<RecipeSuggestionLoad> loadRecipeSuggestion(Long fridgeId){
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<RecipeSuggestion> recipeSuggestionList = recipeSuggestionRepository.findAllByFridge(fridge).orElseThrow(() -> new RecipeSuggestionNotFoundException(fridgeId));
        return recipeSuggestionList.stream().map(i -> RecipeMapper.toRecipeSuggestionLoadDTO(i.getRecipe(), i.getUser().getUserId())).toList();
    }

    public void deleteRecipeSuggestion(Long recipeId, Long fridgeId, Long userId) {
        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new RecipeNotFoundException("Name of the recipe" ,recipeId));
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        User user = userRepository.findUserByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        RecipeSuggestion recipeSuggestion = RecipeSuggestion.builder()
                .id(new RecipeSuggestionId(recipeId, fridgeId, userId))
                .recipe(recipe)
                .fridge(fridge)
                .user(user)
                .build();
        recipeSuggestionRepository.delete(recipeSuggestion);
    }

    public Page<RecipeLoadDTO> getRecipesByName(String recipeName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage;
        if(recipeName != null && !recipeName.isEmpty()) {
            recipePage = recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName, pageable)
                    .orElseThrow(() -> new RecipeNotFoundException("Name of the recipe" , recipeName));
        } else {
            recipePage = recipeRepository.findAll(pageable);
        }
        List<RecipeLoadDTO> recipeLoadDTOs = recipePage.getContent()
                .stream()
                .map(RecipeMapper::toRecipeLoadDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(recipeLoadDTOs, pageable, recipePage.getTotalElements());
    }

    public Page<RecipeLoadDTO> getRecipesByFridgeIdAndDay(Long fridgeId, int page, int size, Day day) {
        Page<Recipe> recipes = itemRecipeScoreService.getRankedRecipeByDate(fridgeId, page, size, day.getNextDateTime());

        if (recipes == null) {
            int numRecipes = (int) recipeRepository.count();

            List<Recipe> subListRecipes = recipeRepository.findRandomSubset(PageRequest.of(0, size, Sort.unsorted()));

            List<RecipeLoadDTO> recipeLoadDTOs = subListRecipes.stream()
                    .map(RecipeMapper::toRecipeLoadDTO)
                    .collect(Collectors.toList());

            return new PageImpl<>(recipeLoadDTOs, PageRequest.of(0, size), numRecipes);
        }

        List<RecipeLoadDTO> recipeLoadDTOs = recipes.getContent()
                .stream()
                .map(recipe -> {
                            RecipeLoadDTO recipeLoadDTO = RecipeMapper.toRecipeLoadDTO(recipe);
                            recipeLoadDTO.setNumMatchingItems(countMatchingItems(recipeLoadDTO, fridgeId));
                            return recipeLoadDTO;
                        }
                )
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(recipeLoadDTOs, pageable, recipes.getTotalElements());
    }

    //TODO: authenticate
    public Page<RecipeLoadDTO> getRecipesByFridgeId(Long fridgeId, int page, int size) {
        Page<Recipe> recipes = itemRecipeScoreService.getRankedRecipesByFridge(fridgeId, page, size);

        if (recipes == null) {
            int numRecipes = (int) recipeRepository.count();

            List<Recipe> subListRecipes = recipeRepository.findRandomSubset(PageRequest.of(0, size, Sort.unsorted()));

            List<RecipeLoadDTO> recipeLoadDTOs = subListRecipes.stream()
                    .map(RecipeMapper::toRecipeLoadDTO)
                    .collect(Collectors.toList());

            return new PageImpl<>(recipeLoadDTOs, PageRequest.of(0, size), numRecipes);
        }

        List<RecipeLoadDTO> recipeLoadDTOs = recipes.getContent()
                .stream()
                .map(recipe -> {
                            RecipeLoadDTO recipeLoadDTO = RecipeMapper.toRecipeLoadDTO(recipe);
                            recipeLoadDTO.setNumMatchingItems(countMatchingItems(recipeLoadDTO, fridgeId));
                            return recipeLoadDTO;
                        }
                )
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(recipeLoadDTOs, pageable, recipes.getTotalElements());
    }

    //TODO: authenticate

    private int countMatchingItems(RecipeLoadDTO recipeDTO, Long fridgeId) {
        Set<Long> fridgeItemIds = fridgeItemsRepository.findAllByFridge_FridgeId(fridgeId)
                .orElseThrow(() -> new FridgeNotFoundException(fridgeId))
                .stream()
                .map(FridgeItems::getItem)
                .map(Item::getItemId)
                .collect(Collectors.toSet());

        return ((Long) recipeDTO.getRecipeParts().stream()
                .map(RecipePartDTO::ingredients)
                .flatMap(Collection::stream)
                .filter(recipeItemDTO -> fridgeItemIds.contains(recipeItemDTO.getItemId()))
                .peek(recipeItemDTO -> recipeItemDTO.setHasItem(true))
                .count())
                .intValue();
    }





}