package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A class that serves as the id for an allergen in a recipe.
 * It is an Embeddable class that contains two fields, recipe and allergen.
 *
 * @author Trym Hamer Gudvangen
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeAllergenId implements Serializable {

    /**
     * The id of the recipe.
     */
    @Column(name = "recipe_id")
    private Long recipe;

    /**
     * The allergen.
     */
    @Column(name = "allergen_id")
    private Long allergen;

}
