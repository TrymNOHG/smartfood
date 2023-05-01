package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
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

    //TODO: add more recipe load things, including pagination of random recipes.

}
