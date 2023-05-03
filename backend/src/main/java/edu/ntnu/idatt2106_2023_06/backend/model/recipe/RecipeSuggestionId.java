package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A class that serves as the id for the RecipeSuggestion entity in the app.
 * It is an Embeddable class that contains three fields, recipe, fridge and user.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeSuggestionId implements Serializable {
    /**
     * The id of the recipe.
     */
    @Column(name = "recipe_id")
    private Long recipeId;

    /**
     * The id of the fridge.
     */
    @Column(name = "fridge_id")
    private Long fridgeId;

    /**
     * The id of the fridge.
     */
    @Column(name = "user_id")
    private Long userId;
}
