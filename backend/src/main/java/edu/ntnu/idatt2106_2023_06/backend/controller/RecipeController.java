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

@RestController
@CrossOrigin("*")
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ItemRecipeScoreService itemRecipeScoreService;
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

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

            // Extract the JSON script from the HTML page
            Element script = document.select("script[type=application/ld+json]").first();
            String scriptText = script.html();

            // Parse the JSON using Jackson's ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(scriptText);

            // Extract the URL from the JSON
            for(JsonNode item : rootNode.get("itemListElement")) {
                String url = item.get("url").asText();
                System.out.println(url);
                recipeService.scrapeRecipe(url);
            }

            if(pageNr == 100) {
                return ResponseEntity.ok().build();
            }
        }

    }

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
        //TODO: free the shopping items from WeekRecipeItems table.
        logger.info("Recipe DTOs successfully made!");
        return ResponseEntity.ok(recipeLoadDTOs);
    }

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


    //TODO: use test to see how effective the algorithm is. Does it need fine tuning.
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

    //TODO: add more recipe load things, including pagination of random recipes.

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

    @PostMapping(value="/suggestion/deny")
    @Operation(summary = "Deny dinner suggestion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint denies a dinner suggestion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)) })}
    )
    public ResponseEntity<Object> denySuggestion(@ParameterObject @RequestParam(name="recipe") Long fridgeId,
                                                   @ParameterObject @RequestParam(name="recipe") Long recipeId,
                                                   @ParameterObject @RequestParam(name="user") Long userId,
                                                   Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info("Attempting to deny suggestion.");
        recipeService.deleteRecipeSuggestion(recipeId, fridgeId, userId);
        logger.info("Suggestion has been denied.");
        return ResponseEntity.ok().build();
    }

    //TODO: add more recipe load things, including pagination of random recipes.

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
