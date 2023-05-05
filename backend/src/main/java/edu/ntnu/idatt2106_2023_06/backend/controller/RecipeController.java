package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.RecipeShoppingDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionAddDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionLoad;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Day;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemRecipeScoreService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.RecipeService;
import edu.ntnu.idatt2106_2023_06.backend.utils.RecipeScraper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * The RecipeController class is responsible for handling requests related to recipes. This includes loading recipes,
 * scraping recipes from external sources, and more.
 *
 * @author Leon Egeberg Hesthaug, Trym Hamer Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ItemRecipeScoreService itemRecipeScoreService;
    private final ItemService itemService;
    private final RecipeScraper recipeScraper;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    /**
     * This endpoint scrapes recipes from Meny.no's website, iteratively searching through their recipes. Recipes are scraped using
     * the {@link RecipeScraper} class.
     *
     * @return              ResponseEntity with status code 200 (OK) if the scraping process completed without error.
     * @throws IOException  if an error occurs during the scraping process.
     */
    @GetMapping(value="/download")
    @Operation(summary = "Get recipe from Meny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)) })}
    )
    public ResponseEntity<Object> downloadRecipe() throws IOException {

        for(int pageNr = 1;; pageNr++) {
            Document document = Jsoup.connect("https://meny.no/oppskrifter/middagstips/?pagenr=" + pageNr).get();

            Element script = document.select("script[type=application/ld+json]").first();
            String scriptText = script.html();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(scriptText);

            for(JsonNode item : rootNode.get("itemListElement")) {
                String url = item.get("url").asText();
                recipeScraper.scrapeRecipe(url);
            }

            if(pageNr == 100) {
                return ResponseEntity.ok().build();
            }
        }

    }


    /**
     * This endpoint retrieves a single recipe with the given name from Meny.
     *
     * @param recipeName    The name of the recipe to retrieve.
     * @return              ResponseEntity containing a RecipeLoadDTO object representing the requested recipe.
     */
    @GetMapping(value="/get")
    @Operation(summary = "Get recipe from Meny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)) })}
    )
    public ResponseEntity<RecipeLoadDTO> loadRecipe(@ParameterObject @RequestParam(name = "recipe") String recipeName) {
        logger.info("Trying to load: " + recipeName);
        RecipeLoadDTO recipeLoadDTO = recipeService.getRecipe(recipeName);
        logger.info("Recipe DTO successfully made!");
        return ResponseEntity.ok(recipeLoadDTO);
    }

    /**
     * This endpoint retrieves a page of recipes with a given name from Meny, allowing for pagination.
     *
     * @param recipeName The name of the recipes to retrieve.
     * @param page       The page number to retrieve.
     * @param size       The number of items to retrieve per page.
     * @return           ResponseEntity containing a Page of RecipeLoadDTO objects representing the requested recipes.
     */
    @GetMapping(value="/load")
    @Operation(summary = "Get recipe from Meny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeLoadDTO.class)) })}
    )
    public ResponseEntity<Page<RecipeLoadDTO>> loadRecipe(
            @ParameterObject @RequestParam(name = "recipe") String recipeName,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Trying to load: " + recipeName);
        Page<RecipeLoadDTO> recipeLoadDTOs = recipeService.getRecipesByName(recipeName, page, size);
        logger.info("Recipe DTOs successfully made!");
        return ResponseEntity.ok(recipeLoadDTOs);
    }

    /**
     * This endpoint retrieves a page of recipes from Meny that use ingredients in the given fridge, allowing for pagination.
     *
     * @param fridgeId The ID of the fridge containing the ingredients to search for.
     * @param page      The page number to retrieve.
     * @param size      The number of items to retrieve per page.
     * @return          ResponseEntity containing a Page of RecipeLoadDTO objects representing the requested recipes.
     */
    @GetMapping(value="/loadByFridge")
    @Operation(summary = "Get recipe from Meny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeLoadDTO.class)) })}
    )
    public ResponseEntity<Page<RecipeLoadDTO>> loadRecipeByFridgeItems(
            @ParameterObject @RequestParam(name = "fridge") Long fridgeId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Trying to load recipes for fridge with ID: " + fridgeId);
        Page<RecipeLoadDTO> recipeLoadDTOs = recipeService.getRecipesByFridgeId(fridgeId, page, size);
        logger.info("Recipe DTOs successfully made!");
        return ResponseEntity.ok(recipeLoadDTOs);
    }

    /**
     * This endpoint retrieves a page of recipes from Meny that use ingredients in the given fridge on the given day,
     * allowing for pagination.
     *
     * @param fridgeId  The ID of the fridge containing the ingredients to search for.
     * @param day       The day of the week to search for recipes on.
     * @param page      The page number to retrieve.
     * @param size      The number of items to retrieve per page.
     * @return          ResponseEntity containing a Page of RecipeLoadDTO objects representing the requested recipes.
     */
    @GetMapping(value="/loadByDay")
    @Operation(summary = "Get recipe from Meny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeLoadDTO.class)) })}
    )
    public ResponseEntity<Page<RecipeLoadDTO>> loadRecipeByFridgeItemsAndDay(
            @ParameterObject @RequestParam(name = "fridge") Long fridgeId,
            @ParameterObject @RequestParam(name = "day") Day day,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Trying to load recipes for fridge with ID: " + fridgeId);
        Page<RecipeLoadDTO> recipeLoadDTOs = recipeService.getRecipesByFridgeIdAndDay(fridgeId, page, size, day);
        logger.info("Recipe DTOs successfully made!");
        return ResponseEntity.ok(recipeLoadDTOs);
    }

    /**
     * This endpoint generates item recipe scores between all items and recipes.
     *
     * @return ResponseEntity with status 200 if the request is successful.
     */
    @PostMapping(value="/generateScores")
    @Operation(summary = "Generate item recipe scores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint generates item recipes scores between all items and recipes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> generateItemRecipeScores() {
        itemRecipeScoreService.generateScores();
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint generates item recipe scores for a specific item and recipe.
     *
     * @param itemId    ID of the item to generate scores for.
     * @param recipeId  ID of the recipe to generate scores for.
     *
     * @return          ResponseEntity with status 200 if the request is successful.
     */
    @PostMapping(value="/generateScoresTest")
    @Operation(summary = "Generate item recipe scores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint generates item recipes scores between all items and recipes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> generateItemRecipeScoresTest(@ParameterObject @RequestParam(name="item") Long itemId,
                                                           @ParameterObject @RequestParam(name="recipe") Long recipeId) {
        itemRecipeScoreService.generateScoreForItem(itemId).join();
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint adds ingredients to the user's shopping list.
     *
     * @param recipeShoppingDTO         DTO containing information about the recipe and the user's fridge.
     * @param authentication            Authentication object containing the user's credentials.
     * @return                          ResponseEntity with status 200 if the request is successful.
     * @throws UnauthorizedException    if the user is not authenticated.
     */
    @PostMapping(value="/addIngredients")
    @Operation(summary = "Add ingredients to shopping list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint adds ingredients to shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> addIngredientToShoppingList(@ParameterObject @RequestBody RecipeShoppingDTO recipeShoppingDTO,
                                                              Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info("Attempting to add ingredients to shopping list.");
        itemService.addIngredientsToShoppingList(recipeShoppingDTO, authentication.getName());
        logger.info("Ingredients have been added.");
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint accepts a dinner suggestion and adds the recipe's ingredients to the user's shopping list.
     *
     * @param recipeShoppingDTO         DTO containing information about the recipe and the user's fridge.
     * @param recipeId                  ID of the recipe being accepted.
     * @param userId                    ID of the user accepting the suggestion.
     * @param authentication            Authentication object containing the user's credentials.
     * @return                          ResponseEntity with status 200 if the request is successful.
     * @throws UnauthorizedException    if the user is not authenticated.
     */
    @PostMapping(value="/suggestion/accept")
    @Operation(summary = "Accept dinner suggestion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint accepts a dinner suggestion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> acceptSuggestion(@ParameterObject @RequestBody RecipeShoppingDTO recipeShoppingDTO,
                                                   @ParameterObject @RequestParam(name="recipe") Long recipeId,
                                                   @ParameterObject @RequestParam(name="user") Long userId,
                                                Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info("Attempting to accept suggestion.");
        itemService.addIngredientsToShoppingList(recipeShoppingDTO, authentication.getName());
        recipeService.deleteRecipeSuggestion(recipeId, recipeShoppingDTO.fridgeId(), userId);
        logger.info("Suggestion has been accepted.");
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint denies a dinner suggestion.
     *
     * @param fridgeId                  The ID of the fridge where the recipe suggestion was made.
     * @param recipeId                  The ID of the recipe that was suggested.
     * @param userId                    The ID of the user who made the suggestion.
     * @param authentication            The authentication object for the current user.
     * @return                          ResponseEntity object with HTTP status 200 if the suggestion was successfully denied.
     * @throws UnauthorizedException    if the user is not authenticated.
     */

    @PostMapping(value="/suggestion/deny")
    @Operation(summary = "Deny dinner suggestion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint denies a dinner suggestion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> denySuggestion(@ParameterObject @RequestParam(name="fridge") Long fridgeId,
                                                   @ParameterObject @RequestParam(name="recipe") Long recipeId,
                                                   @ParameterObject @RequestParam(name="user") Long userId,
                                                   Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info("Attempting to deny suggestion.");
        recipeService.deleteRecipeSuggestion(recipeId, fridgeId, userId);
        logger.info("Suggestion has been denied.");
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint adds a dinner suggestion.
     *
     * @param recipeSuggestionAddDTO a DTO object representing the details of the recipe suggestion to be added.
     * @param authentication the authentication object for the current user.
     * @return a ResponseEntity object with HTTP status 200 if the suggestion was successfully added.
     * @throws UnauthorizedException if the user is not authenticated.
     */
    @PostMapping(value="/suggestion/add")
    @Operation(summary = "Add dinner suggestion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint adds dinner suggestion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> addSuggestion(@ParameterObject @RequestBody RecipeSuggestionAddDTO recipeSuggestionAddDTO,
                                                              Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info("Attempting to add suggestion.");
        recipeService.addRecipeSuggestion(recipeSuggestionAddDTO);
        logger.info("Suggestion has been added.");
        return ResponseEntity.ok().build();
    }

    /**
     *  This endpoint loads the dinner suggestions for a given fridge.
     *
     *  @param fridgeId The ID of the fridge for which to load the suggestions.
     *  @return         ResponseEntity object with HTTP status 200 and a list of RecipeSuggestionLoad objects if
     *                  the suggestions were loaded successfully.
     */
    @GetMapping(value="/suggestion/load")
    @Operation(summary = "Add dinner suggestion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint loads dinner suggestions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeSuggestionLoad.class)) })}
    )
    public ResponseEntity<Object> loadSuggestion(@ParameterObject @RequestParam(name = "fridge") Long fridgeId) {

        logger.info("Attempting to load suggestions.");
        List<RecipeSuggestionLoad> recipeSuggestionLoadList = recipeService.loadRecipeSuggestion(fridgeId);
        logger.info("Suggestions has been loaded.");
        return ResponseEntity.ok(recipeSuggestionLoadList);
    }

}
