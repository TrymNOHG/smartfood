package edu.ntnu.idatt2106_2023_06.backend.service.items;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Allergen;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Amount;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergen;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.AllergenRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeAllergenRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.recipe.RecipeRepository;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final AllergenRepository allergenRepository;
    private final RecipeAllergenRepository recipeAllergenRepository;

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

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(recipeAPIAddress)
                .build();

        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
//            System.out.println(responseBody);
            //TODO: parse response body
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
            System.out.println("Name: " + recipeName);
            System.out.println("Recipe Desc: " + recipeDesc);
            System.out.println("Difficulty: " + difficultyEstimate);
            System.out.println("Number of people: " + servingSize);
            System.out.println("Time Estimate: " + timeEstimate);
            System.out.println("Thumbnail: " + thumbnailLink);

            //TODO: fix
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
                    .recipeAllergenSet(new HashSet<>())
                    .build();

            logger.info("Saving recipe to database.");
            recipeRepository.save(recipe);
            System.out.println(recipe);

            //Parsing the recipeDetails
            JsonNode recipeDetailsNode = rootNode.at("/_source/recipeDetails");
            if (recipeDetailsNode.isArray() && recipeDetailsNode.size() > 0) {
                for(JsonNode recipePart : recipeDetailsNode) {
                    String partName = recipePart.at("/name").asText();
                    for(JsonNode ingredient : recipePart.at("/ingredients")) {
                        JsonNode product = ingredient.at("/products").get(0);

                        /*
                            ---- SAVING RECIPE ALLERGENS ----
                         */
                        for(JsonNode allergenNode : product.at("/allergens")) {
                            if (allergenNode.get("code").asText().equals("FRI")) continue;

                            Amount amount = switch (allergenNode.get("code").asText()) {
                                case "KAN" -> Amount.TRACE;
                                case "JA" -> Amount.PRESENT;
                                default -> throw new NullPointerException();
                            };

                            String allergenName = allergenNode.get("displayName").asText();
                            Objects.requireNonNull(allergenName);

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
                                    .recipe(recipe)
                                    .allergen(allergen)
                                    .amount(amount)
                                    .build();

                            recipeAllergenRepository.save(recipeAllergen);

                        }

                        /*
                            ---- END OF SAVING RECIPE ALLERGENS ----
                         */

                        //TODO: create RecipeItem, get EAN here
                    }
                    JsonNode steps = recipePart.at("/steps");
                    //TODO: get steps
                    StringBuilder sb = new StringBuilder();
                    for(JsonNode step : steps) {
                        //Could add picture to instruction using imageID and https://res.cloudinary.com/norgesgruppen/image/upload/f_auto,q_50,dpr_2.0,w_700,h_350,c_fill/IMAGE_ID

                        sb.append(step.at("/description").asText());
                    }

                    System.out.println("Part name: " + partName);
                    System.out.println("Steps: " + sb.toString());
                }

            }

        }


    }

}


/*
    {
"recipeId": 1,
"recipeName": "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
"description": "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en rekke grønnsaksrester.\n",
"author": "Meny",
"servingSize": 5,
"difficulty": 1,
"thumbnailLink": "image.png",
"cookTime": 50.0,
"recipeParts": [
{
"recipePartId": 1,
"partTitle": "Make dough",
"partDescription": "Step 1. Knead dough",
"recipeItems": [{
"itemId" : 1,
"quantity": 1,
"unit" : "L"
}]
}
],
"allergens": ["Dairy"]
}

 */