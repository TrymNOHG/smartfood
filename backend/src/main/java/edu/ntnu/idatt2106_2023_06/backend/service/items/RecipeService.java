package edu.ntnu.idatt2106_2023_06.backend.service.items;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.*;
import jakarta.transaction.Transactional;
import lombok.*;
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

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final AllergenRepository allergenRepository;
    private final RecipeAllergenRepository recipeAllergenRepository;
    private final InstructionRepository instructionRepository;
    private final RecipePartRepository recipePartRepository;

    @Transactional
    public void scrapeRecipe(String recipePageURL) throws IOException {
        final Document document = Jsoup.connect(recipePageURL).get();

        Element recipeComponent = document.selectFirst("div[data-component=RecipeWeb]");
        assert recipeComponent != null;
        String recipeId = recipeComponent.attr("data-prop-recipe-id");
        System.out.println(recipeId);

        //TODO: look at getting all of the recipe urls out

        String recipeAPIAddress = String.format("https://platform-rest-prod.ngdata.no/api/recipes2/1300/" +
                "%s?store_id=7080001150488&full_response=true&fieldset=maximal", recipeId);

//        System.out.println(recipeAPIAddress);
        //e8a56586-7813-5ee5-d4e6-d315b9fc6179
//        https://platform-rest-prod.ngdata.no/api/recipes2/1300/e8a56586-7813-5ee5-d4e6-d315b9fc6179?store_id=7080001150488&full_response=true&fieldset=maximal

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(recipeAPIAddress)
                .build();

        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            String recipeName = rootNode.at("/_source/name").asText();
            String recipeDesc = rootNode.at("/_source/description").asText();
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

            Recipe recipe = Recipe
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

            logger.info("Saving recipe to database.");
            recipe = recipeRepository.save(recipe);
            System.out.println(recipe);

            //Parsing the recipeDetails
            JsonNode recipeDetailsNode = rootNode.at("/_source/recipeDetails");
            boolean stepsAdded = false;
            for(JsonNode recipePartNode : recipeDetailsNode) {
                String partName = recipePartNode.at("/name").asText();

                RecipePart recipePart = RecipePart
                        .builder()
                        .partName(partName == null ? "" : partName)
                        .recipe(recipe)
                        .itemsInRecipe(new ArrayList<>())
                        .build();

                recipePartRepository.save(recipePart);
                System.out.println(recipePart);

                for(JsonNode ingredient : recipePartNode.at("/ingredients")) {

                    JsonNode product = ingredient.at("/products").get(0);
                    if(product == null) continue;
                     /*
                            ---- SAVING RECIPE ALLERGENS ----
                         */
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

                        System.out.println(allergen);

                        RecipeAllergen recipeAllergen = RecipeAllergen
                                .builder()
                                .id(new RecipeAllergenId(recipe.getRecipeId(), allergen.getAllergenId()))
                                .recipe(recipe)
                                .allergen(allergen)
                                .amount(amount)
                                .build();

                        recipeAllergenRepository.save(recipeAllergen);
                        System.out.println(recipeAllergen);
                    }

                        /*
                            ---- END OF SAVING RECIPE ALLERGENS ----
                         */

                    /*
                        ---- SAVING RECIPE ITEM ----
                     */

                    //TODO: if item does not already exist in database, register it and then use the item created to create recipe item
                    //TODO: need to store EAN in database...
                    String EAN = product.get("ean").asText();
                    int measurementValue = ingredient.get("measurementValue").asInt();
                    String unit = ingredient.get("measurementType").asText();
                    RecipeItems recipeItem = RecipeItems
                            .builder()
                            .id(new RecipeItemId(1L, recipePart.getRecipePartId())) //TODO: add itemID
                            .item(new Item()) //TODO
                            .recipePart(recipePart)
                            .quantity(measurementValue)
                            .unitOfMeasurement(unit)
                            .build();

                    System.out.println(recipeItem + "    " + EAN);

                    //TODO: first add EAN to an item, change the itemDTO to include this...
                    //TODO: second, create a call to frontend to create an itemDTO with a given EAN
//                    Can get List of items by EAN and pick first.
                    //TODO: create RecipeItem, get EAN here
                }
                /*
                    ---- END OF SAVING RECIPE ITEM ----
                 */

                /*
                           ---- SAVING INSTRUCTIONS FOR RECIPE ----
                 */
                if(!stepsAdded) {
                    JsonNode steps = recipePartNode.at("/recipeSteps");
                    if(steps.isArray() && steps.size() > 0) {
                        for (JsonNode step : steps) {
                            JsonNode imgId = step.get("/imageId");
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
                            System.out.println(instruction);
                        }
                        stepsAdded = true;
                    }
                }

                 /*
                           ---- END OF SAVING INSTRUCTIONS FOR RECIPE ----
                 */
                System.out.println("Part name: " + partName);
            }
        }
    }
}