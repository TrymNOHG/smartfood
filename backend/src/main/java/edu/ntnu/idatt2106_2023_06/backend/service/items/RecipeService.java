package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Recipe;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RecipeService {

    public void scrapeRecipe(String recipePageURL) throws IOException {
        Recipe recipe = new Recipe();

        final Document document = Jsoup.connect(recipePageURL).get();

        Element recipeComponent = document.selectFirst("div[data-component=RecipeWeb]");
        assert recipeComponent != null;
        String recipeId = recipeComponent.attr("data-prop-recipe-id");
        System.out.println(recipeId);

        String recipeAPIAddress = String.format("https://platform-rest-prod.ngdata.no/api/recipes2/1300/" +
                "%s?store_id=7080001150488&full_response=true&fieldset=maximal", recipeId);

        System.out.println(recipeAPIAddress);

        //TODO: perform Get on recipeAPIAddress
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(recipeAPIAddress)
                .build();

        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            System.out.println(responseBody);
            //TODO: parse response body
        }

    }

}
