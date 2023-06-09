package edu.ntnu.idatt2106_2023_06.backend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeScraper {

    private final RecipeRepository recipeRepository;
    private final RecipePartRepository recipePartRepository;
    private final AllergenRepository allergenRepository;
    private final RecipeAllergenRepository recipeAllergenRepository;
    private final RecipeItemsRepository recipeItemsRepository;
    private final InstructionRepository instructionRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(RecipeScraper.class);


    @Transactional
    public void scrapeRecipe(String recipePageURL) throws IOException {
        logger.info("Scraping recipe started.");

        Document document = Jsoup.connect(recipePageURL).get();
        Element recipeComponent = document.selectFirst("div[data-component=RecipeWeb]");

        assert recipeComponent != null;
        String recipeId = recipeComponent.attr("data-prop-recipe-id");
        logger.info("Recipe id: {}", recipeId);

        String recipeAPIAddress = String.format("https://platform-rest-prod.ngdata.no/api/recipes2/1300/%s?store_id=7080001150488&full_response=true&fieldset=maximal", recipeId);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(recipeAPIAddress)
                .build();

        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            JsonNode rootNode = new ObjectMapper().readTree(responseBody);

            Recipe tempRecipe = createRecipeFromJsonNode(rootNode);
            if(tempRecipe == null) return;

            logger.info("Saving recipe to database.");
            final Recipe recipe = recipeRepository.save(tempRecipe);

            boolean stepsAdded = false;

            for(JsonNode recipePartNode : rootNode.at("/_source/recipeDetails")) {

                RecipePart recipePart = createRecipePartFromJsonNode(recipePartNode, recipe);

                for(JsonNode ingredient : recipePartNode.at("/ingredients")) {

                    Optional.ofNullable(ingredient.at("/products")).ifPresent(products ->{
                                if(products.get(0) == null) return;
                                addAllergens(products.get(0), recipe);
                                addRecipeItem(ingredient, recipePart);
                            }
                    );

                }

                if(!stepsAdded) {
                    JsonNode steps = recipePartNode.at("/recipeSteps");
                    if(steps.isArray() && steps.size() > 0) {
                        addInstructions(steps, recipe);
                        stepsAdded = true;
                    }
                }
            }
        }
    }

    private Recipe createRecipeFromJsonNode(JsonNode rootNode) {
        logger.info("Creating recipe");
        String recipeName = rootNode.at("/_source/name").asText();
        String recipeDesc = rootNode.at("/_source/description").asText();

        Recipe recipe = recipeRepository.findRecipeByRecipeNameContainingIgnoreCase(recipeName)
                .orElse(null);

        if(recipe != null && recipe.getDescription().equals(recipeDesc)) {
            return null;
        }

        String author = "Meny";
        int servingSize = rootNode.at("/_source/numberOfPersons").asInt();
        int difficultyEstimate = rootNode.at("/_source/difficultyEstimate").asInt();
        String imageId = rootNode.at("/_source/media/imageId").asText();
        String thumbnailLink = "https://res.cloudinary.com/norgesgruppen/images/c_scale,dpr_auto,f_auto,q_auto:eco,w_1600/" +
                imageId + "/" + recipeName.replace(" ", "-")
                .replace(",", "")
                .replace("ø", "o")
                .replace("å", "a")
                .replace("æ","ae")
                .toLowerCase();
        int timeEstimate = rootNode.at("/_source/timeEstimate").asInt();

        return Recipe
                .builder()
                .recipeName(recipeName)
                .description(recipeDesc)
                .author(author)
                .servingSize(servingSize)
                .difficulty(difficultyEstimate)
                .thumbnailLink(thumbnailLink)
                .cookTime(timeEstimate)
                .recipeParts(new ArrayList<>())
                .instructions(new ArrayList<>())
                .recipeAllergenSet(new HashSet<>())
                .build();
    }

    private RecipePart createRecipePartFromJsonNode(JsonNode recipePartNode, Recipe recipe) {
        logger.info("Creating recipe part");
        String partName = recipePartNode.at("/name").asText();

        RecipePart recipePart = RecipePart
                .builder()
                .partName(partName == null ? "" : partName)
                .recipe(recipe)
                .itemsInRecipe(new ArrayList<>())
                .build();

        return recipePartRepository.save(recipePart);
    }

    private void addAllergens(JsonNode product, Recipe recipe) {
        logger.info("Adding allergens to recipe");
        for(JsonNode allergenNode : product.at("/allergens")) {
            //If nullpointer happens, then fix if below to be if not kan eller ja
            if (!allergenNode.get("code").asText().equals("KAN") && !allergenNode.get("code").asText().equals("JA")) continue;

            Amount amount = switch (allergenNode.get("code").asText()) {
                case "KAN" -> Amount.TRACE;
                case "JA" -> Amount.PRESENT;
                default -> throw new NullPointerException();
            };

            if(allergenNode.get("displayName") == null) continue;

            String allergenName = allergenNode.get("displayName").asText();


            Allergen allergen = allergenRepository.findAllergenByAllergenName(allergenName)
                    .orElseGet(() ->
                            allergenRepository.save(Allergen
                                    .builder()
                                    .allergenName(allergenName)
                                    .recipeAllergenSet(new HashSet<>())
                                    .build()
                            )
                    );

            RecipeAllergen recipeAllergen = RecipeAllergen
                    .builder()
                    .id(new RecipeAllergenId(recipe.getRecipeId(), allergen.getAllergenId()))
                    .recipe(recipe)
                    .allergen(allergen)
                    .amount(amount)
                    .build();

            recipeAllergenRepository.save(recipeAllergen);
        }
    }

    private void addRecipeItem(JsonNode ingredient, RecipePart recipePart) {
        logger.info("Adding recipe item");
        String EAN = ingredient.at("/products").get(0).get("ean").asText();
        int measurementValue = ingredient.get("measurementValue").asInt();
        String unit = ingredient.get("measurementType").asText();

        Item itemOfRecipe = itemRepository.findItemByEanAndStore_StoreName(EAN, "Meny")
                .orElseGet(() -> createItemByEAN(EAN));

        RecipeItems recipeItem = RecipeItems
                .builder()
                .id(new RecipeItemId(itemOfRecipe.getItemId(), recipePart.getRecipePartId()))
                .item(itemOfRecipe)
                .recipePart(recipePart)
                .quantity(measurementValue)
                .unitOfMeasurement(unit)
                .build();

        recipeItemsRepository.save(recipeItem);
        System.out.println(recipeItem + "    " + EAN);
    }

    private void addInstructions(JsonNode steps, Recipe recipe) {
        logger.info("Adding instructions to recipe");
        for (JsonNode step : steps) {
            JsonNode imgId = step.get("imageId");
            String stepImageLink = imgId == null ? "" :
                    "https://res.cloudinary.com/norgesgruppen/image/upload/f_auto,q_50,dpr_2.0,w_700,h_350,c_fill/"
                            + imgId.asText();

            String description = step.at("/description").asText();
            Instructions instruction = Instructions
                    .builder()
                    .instruction(description)
                    .imageLink(stepImageLink)
                    .recipe(recipe)
                    .build();

            instructionRepository.save(instruction);
        }
    }

    @Transactional
    public Item createItemByEAN(String ean){
        logger.info("Creating item by EAN");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://kassal.app/api/v1/products/ean/" + ean)
                .header("Authorization", "Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX") //TODO: use ApiConfig instead
                .build();

        try(Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            for(JsonNode product : rootNode.at("/data/products")) {
                if(!product.get("store").get("name").asText().equals("Meny")) continue;
                ItemDTO itemDTO = ItemDTO
                        .builder()
                        .EAN(ean)
                        .price(product.get("current_price").get("price").asDouble())
                        .name(product.get("name").asText())
                        .store("Meny")
                        .quantity(1)
                        .image(product.get("image").asText())
                        .description(product.get("description").asText())
                        .suggestion(false)
                        .build();

                return itemService.addItem(itemDTO);
            }
        }
        catch (Exception ignored) {

        }
        return null;
    }

}
